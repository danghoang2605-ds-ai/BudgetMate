package com.example.savemoneytime.util;

/** Minimal RFC 4180 field escaping for the CSV export. */
public final class Csv {

    private Csv() {}

    public static String field(String value) {
        if (value == null) return "";
        boolean quote = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");
        String escaped = value.replace("\"", "\"\"");
        return quote ? "\"" + escaped + "\"" : escaped;
    }

    public static String row(String... values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(field(values[i]));
        }
        return sb.append('\n').toString();
    }
}
