package com.example.savemoneytime.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class UrlsTest {
    @Test
    public void upgradesPlainHttpOnly() {
        assertEquals("https://news.example/a", Urls.https("http://news.example/a"));
        assertEquals("https://news.example/a", Urls.https("HTTP://news.example/a".replace("HTTP", "http")));
        assertEquals("https://x.y", Urls.https("https://x.y"));
        assertEquals("", Urls.https(""));
        assertNull(Urls.https(null));
    }
}
