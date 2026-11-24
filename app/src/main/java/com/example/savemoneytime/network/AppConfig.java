package com.example.savemoneytime.network;

import com.example.savemoneytime.BuildConfig;

/** API keys injected at build time from local.properties (see README). Never hard-code keys here. */
public final class AppConfig {
    public static final String OPENROUTER_KEY   = BuildConfig.OPENROUTER_KEY;
    public static final String GNEWS_KEY        = BuildConfig.GNEWS_KEY;
    public static final String ALPHAVANTAGE_KEY = BuildConfig.ALPHAVANTAGE_KEY;
    /** If set, AI requests go to this proxy (proxy/ in the repo) and no AI key is needed in the app. */
    public static final String AI_PROXY_URL     = BuildConfig.AI_PROXY_URL;
    public static final String AI_APP_TOKEN     = BuildConfig.AI_APP_TOKEN;

    private AppConfig() {}

    public static boolean has(String key) {
        return key != null && !key.trim().isEmpty();
    }
}