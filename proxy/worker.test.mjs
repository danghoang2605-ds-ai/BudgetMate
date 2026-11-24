import assert from "node:assert/strict";
import { test } from "node:test";
import worker, { MAX_TOKENS, MODEL, sanitize } from "./worker.js";

const ok = { model: "openai/gpt-4o", max_tokens: 5000, messages: [{ role: "system", content: "s" }, { role: "user", content: "Boba 6$" }] };

test("pins the model and caps tokens", () => {
  const { body } = sanitize(ok);
  assert.equal(body.model, MODEL);
  assert.equal(body.max_tokens, MAX_TOKENS);
  assert.equal(body.messages.length, 2);
});

test("rejects bad payloads", () => {
  assert.ok(sanitize(null).error);
  assert.ok(sanitize({ messages: [] }).error);
  assert.ok(sanitize({ messages: [{ role: "assistant", content: "x" }] }).error);
  assert.ok(sanitize({ messages: [{ role: "user", content: "x".repeat(5000) }] }).error);
});

test("requires the app token when configured", async () => {
  const req = new Request("https://proxy.test", { method: "POST", body: JSON.stringify(ok) });
  const res = await worker.fetch(req, { APP_TOKEN: "secret", OPENROUTER_API_KEY: "k" });
  assert.equal(res.status, 401);
});

test("forwards a clean request upstream", async () => {
  const calls = [];
  globalThis.fetch = async (url, init) => {
    calls.push({ url, init });
    return new Response(JSON.stringify({ choices: [{ message: { content: "{}" } }] }), { status: 200 });
  };
  const req = new Request("https://proxy.test", {
    method: "POST", headers: { "X-App-Token": "secret" }, body: JSON.stringify(ok),
  });
  const res = await worker.fetch(req, { APP_TOKEN: "secret", OPENROUTER_API_KEY: "k" });
  assert.equal(res.status, 200);
  assert.equal(calls[0].init.headers.Authorization, "Bearer k");
  assert.equal(JSON.parse(calls[0].init.body).model, MODEL);
});

test("GET is rejected", async () => {
  const res = await worker.fetch(new Request("https://proxy.test"), {});
  assert.equal(res.status, 405);
});
