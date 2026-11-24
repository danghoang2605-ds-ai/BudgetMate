package com.example.savemoneytime.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Turns the chatbot's raw reply into a typed result.
 *
 * Pure Java (only Gson), so it can be unit-tested on the JVM without an emulator.
 * Handles replies wrapped in ```json fences or surrounded by stray text, and
 * amounts sent as numbers or strings such as "$6.50".
 */
public final class AiReplyParser {

    public enum Kind { EXPENSE, REVENUE, MESSAGE }

    public static final class Result {
        public final Kind kind;
        public final long amount;
        public final String category;
        public final String note;
        public final String message;

        Result(Kind kind, long amount, String category, String note, String message) {
            this.kind = kind;
            this.amount = amount;
            this.category = category;
            this.note = note;
            this.message = message;
        }

        public boolean isTransaction() { return kind != Kind.MESSAGE; }
    }

    private AiReplyParser() {}

    public static Result parse(String raw) {
        if (raw == null) return message("");
        String text = raw.replace("```json", "").replace("```", "").trim();
        JsonObject json = extractObject(text);
        if (json == null) return message(text);

        if (json.has("type")) {
            String type = asString(json, "type", "").toUpperCase();
            long amount = parseAmount(json.get("amount"));
            if (amount > 0 && ("EXPENSE".equals(type) || "REVENUE".equals(type) || "INCOME".equals(type))) {
                String category = asString(json, "category", "Other");
                String note = asString(json, "note", "AI Log");
                Kind kind = "EXPENSE".equals(type) ? Kind.EXPENSE : Kind.REVENUE;
                return new Result(kind, amount, category, note, null);
            }
        }
        if (json.has("message")) return message(asString(json, "message", text));
        return message(text);
    }

    /** Rounds to the nearest whole unit, matching how amounts are stored. Returns 0 if unreadable. */
    static long parseAmount(JsonElement el) {
        if (el == null || el.isJsonNull() || !el.isJsonPrimitive()) return 0;
        try {
            String s = el.getAsString().replaceAll("[^0-9.\\-]", "");
            if (s.isEmpty()) return 0;
            return Math.max(0, Math.round(Double.parseDouble(s)));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static JsonObject extractObject(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        if (start < 0 || end <= start) return null;
        try {
            JsonElement el = JsonParser.parseString(text.substring(start, end + 1));
            return el.isJsonObject() ? el.getAsJsonObject() : null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    private static String asString(JsonObject o, String key, String fallback) {
        JsonElement el = o.get(key);
        if (el == null || el.isJsonNull()) return fallback;
        try {
            String s = el.getAsString().trim();
            return s.isEmpty() ? fallback : s;
        } catch (RuntimeException e) {
            return fallback;
        }
    }

    private static Result message(String text) {
        return new Result(Kind.MESSAGE, 0, null, null, text);
    }
}
