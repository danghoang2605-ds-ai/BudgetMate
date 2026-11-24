package com.example.savemoneytime.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.gson.JsonPrimitive;
import org.junit.Test;

public class AiReplyParserTest {

    @Test
    public void parsesExpense() {
        AiReplyParser.Result r = AiReplyParser.parse(
                "{\"amount\": 6, \"category\": \"Food\", \"note\": \"Boba tea\", \"type\": \"EXPENSE\"}");
        assertEquals(AiReplyParser.Kind.EXPENSE, r.kind);
        assertEquals(6, r.amount);
        assertEquals("Food", r.category);
        assertEquals("Boba tea", r.note);
    }

    @Test
    public void parsesRevenueInsideMarkdownFence() {
        AiReplyParser.Result r = AiReplyParser.parse(
                "```json\n{\"amount\": \"$2,000\", \"category\": \"Salary\", \"type\": \"REVENUE\"}\n```");
        assertEquals(AiReplyParser.Kind.REVENUE, r.kind);
        assertEquals(2000, r.amount);
        assertEquals("AI Log", r.note);
    }

    @Test
    public void toleratesTextAroundJson() {
        AiReplyParser.Result r = AiReplyParser.parse(
                "Sure! {\"amount\": 15.6, \"category\": \"Food\", \"type\": \"expense\"} done");
        assertEquals(AiReplyParser.Kind.EXPENSE, r.kind);
        assertEquals(16, r.amount);
    }

    @Test
    public void queryBecomesMessage() {
        AiReplyParser.Result r = AiReplyParser.parse("{\"intent\": \"query\", \"message\": \"Hello!\"}");
        assertFalse(r.isTransaction());
        assertEquals("Hello!", r.message);
    }

    @Test
    public void zeroOrMissingAmountIsNotSaved() {
        assertFalse(AiReplyParser.parse("{\"amount\": 0, \"type\": \"EXPENSE\"}").isTransaction());
        assertFalse(AiReplyParser.parse("{\"type\": \"EXPENSE\"}").isTransaction());
    }

    @Test
    public void plainTextPassesThrough() {
        AiReplyParser.Result r = AiReplyParser.parse("I could not understand that.");
        assertEquals(AiReplyParser.Kind.MESSAGE, r.kind);
        assertEquals("I could not understand that.", r.message);
        assertEquals("", AiReplyParser.parse(null).message);
    }

    @Test
    public void amountParsing() {
        assertEquals(7, AiReplyParser.parseAmount(new JsonPrimitive("7$")));
        assertEquals(0, AiReplyParser.parseAmount(new JsonPrimitive("abc")));
        assertEquals(0, AiReplyParser.parseAmount(new JsonPrimitive(-5)));
        assertTrue(AiReplyParser.parseAmount(new JsonPrimitive(1e6)) == 1_000_000);
    }
}
