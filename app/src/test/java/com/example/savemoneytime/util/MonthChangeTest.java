package com.example.savemoneytime.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class MonthChangeTest {

    @Test
    public void previousMonthWrapsTheYear() {
        assertArrayEquals(new String[]{"12", "2025"}, MonthChange.previous("01", "2026"));
        assertArrayEquals(new String[]{"10", "2026"}, MonthChange.previous("11", "2026"));
    }

    @Test
    public void labels() {
        assertEquals("↑ 50.0%", MonthChange.label(1500, 1000));
        assertEquals("↓ 25.0%", MonthChange.label(750, 1000));
        assertEquals("↑ 150.0%", MonthChange.label(500, -1000));
        assertEquals("New", MonthChange.label(300, 0));
        assertEquals("—", MonthChange.label(0, 0));
        assertNull(MonthChange.percent(5, 0));
    }
}
