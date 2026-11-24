# BudgetMate AI proxy

A ~60-line Cloudflare Worker that keeps the OpenRouter key off the phone.

```
Android app ──X-App-Token──▶ Worker (holds OPENROUTER_API_KEY) ──▶ OpenRouter
```

The Worker accepts only `system`/`user` messages, pins the model to `google/gemini-2.5-flash`
and caps `max_tokens` at 400, so even a leaked app token cannot be used for anything expensive.

## Deploy (free tier is enough)

```bash
cd proxy
npx wrangler login
npx wrangler secret put OPENROUTER_API_KEY   # your OpenRouter key
npx wrangler secret put APP_TOKEN            # any long random string
npx wrangler deploy                          # prints https://budgetmate-ai-proxy.<you>.workers.dev
```

Then in the app's `local.properties`:

```properties
ai.proxy.url=https://budgetmate-ai-proxy.<you>.workers.dev
ai.app.token=<the same APP_TOKEN>
```

and leave `openrouter.api.key` empty.

## Test

```bash
cd proxy && npm test
```
