package com.example.savemoneytime.util;

/** Cleartext traffic is disabled, so plain-http links from news APIs are upgraded to https. */
public final class Urls {

    private Urls() {}

    public static String https(String url) {
        if (url == null) return null;
        String u = url.trim();
        return u.regionMatches(true, 0, "http://", 0, 7) ? "https://" + u.substring(7) : u;
    }
}
