package com.example.savemoneytime.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CsvTest {

    @Test
    public void plainValuesAreUnchanged() {
        assertEquals("Food", Csv.field("Food"));
        assertEquals("", Csv.field(null));
    }

    @Test
    public void commasQuotesAndNewlinesAreQuoted() {
        assertEquals("\"Lunch, with team\"", Csv.field("Lunch, with team"));
        assertEquals("\"5\"\" screen\"", Csv.field("5\" screen"));
        assertEquals("\"a\nb\"", Csv.field("a\nb"));
    }

    @Test
    public void rowJoinsFields() {
        assertEquals("EXPENSE,Food,12,\"Pho, extra beef\"\n", Csv.row("EXPENSE", "Food", "12", "Pho, extra beef"));
    }
}
