package com.example.savemoneytime.util;

import java.util.Locale;

/** Month-over-month helpers, kept free of Android types so they run in plain JVM tests. */
public final class MonthChange {

    private MonthChange() {}

    /** {"MM", "yyyy"} of the month before the given one. */
    public static String[] previous(String month, String year) {
        int m = Integer.parseInt(month);
        int y = Integer.parseInt(year);
        if (m == 1) {
            m = 12;
            y -= 1;
        } else {
            m -= 1;
        }
        return new String[]{String.format(Locale.US, "%02d", m), String.valueOf(y)};
    }

    /** Percent change from previous to current, relative to |previous|; null when there is no baseline. */
    public static Double percent(long current, long previous) {
        if (previous == 0) return null;
        return (current - previous) * 100.0 / Math.abs(previous);
    }

    /** Pill text such as "↑ 12.5%", "↓ 4.0%", or "New" when last month had no activity. */
    public static String label(long current, long previous) {
        Double pct = percent(current, previous);
        if (pct == null) return current == 0 ? "—" : "New";
        return (pct >= 0 ? "↑ " : "↓ ") + String.format(Locale.US, "%.1f%%", Math.abs(pct));
    }
}
