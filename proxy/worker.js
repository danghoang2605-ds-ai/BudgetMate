/**
 * BudgetMate AI proxy (Cloudflare Worker).
 *
 * The Android app sends chat requests here instead of to OpenRouter, so the OpenRouter key
 * lives only in this Worker's secrets and cannot be pulled out of the APK. The Worker also
 * pins the model and caps tokens, so a leaked app token cannot run up an expensive bill.
 *
 * Secrets: OPENROUTER_API_KEY (required), APP_TOKEN (recommended).
 */

export const MODEL = "google/gemini-2.5-flash";
export const MAX_TOKENS = 400;
export const MAX_MESSAGES = 4;
export const MAX_CHARS = 12000; // system prompt (~1k) + receipt instructions + up to 6k of OCR text
const UPSTREAM = "https://openrouter.ai/api/v1/chat/completions";

/** Validates the app's payload and returns the body to send upstream, or an error string. */
export function sanitize(payload) {
  if (!payload || typeof payload !== "object") return { error: "Body must be a JSON object" };
  const { messages } = payload;
  if (!Array.isArray(messages) || messages.length === 0 || messages.length > MAX_MESSAGES) {
    return { error: `messages must be an array of 1-${MAX_MESSAGES} items` };
  }
  let chars = 0;
  const clean = [];
  for (const m of messages) {
    if (!m || !["system", "user"].includes(m.role) || typeof m.content !== "string") {
      return { error: "each message needs role system|user and string content" };
    }
    chars += m.content.length;
    clean.push({ role: m.role, content: m.content });
  }
  if (chars > MAX_CHARS) return { error: `messages exceed ${MAX_CHARS} characters` };
  const requested = Number(payload.max_tokens) || MAX_TOKENS;
  return { body: { model: MODEL, max_tokens: Math.min(Math.max(1, requested), MAX_TOKENS), messages: clean } };
}

function json(status, obj) {
  return new Response(JSON.stringify(obj), { status, headers: { "Content-Type": "application/json" } });
}

export default {
  async fetch(request, env) {
    if (request.method !== "POST") return json(405, { error: { message: "POST only" } });
    if (env.APP_TOKEN && request.headers.get("X-App-Token") !== env.APP_TOKEN) {
      return json(401, { error: { message: "Invalid app token" } });
    }
    if (!env.OPENROUTER_API_KEY) return json(500, { error: { message: "Proxy is missing OPENROUTER_API_KEY" } });

    let payload;
    try {
      payload = await request.json();
    } catch {
      return json(400, { error: { message: "Body is not valid JSON" } });
    }
    const { body, error } = sanitize(payload);
    if (error) return json(400, { error: { message: error } });

    const upstream = await fetch(UPSTREAM, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${env.OPENROUTER_API_KEY}`,
        "Content-Type": "application/json",
        "X-Title": "BudgetMate AI",
      },
      body: JSON.stringify(body),
    });
    return new Response(upstream.body, {
      status: upstream.status,
      headers: { "Content-Type": upstream.headers.get("Content-Type") || "application/json" },
    });
  },
};
