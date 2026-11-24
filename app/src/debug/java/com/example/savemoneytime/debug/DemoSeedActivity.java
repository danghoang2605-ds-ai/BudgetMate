package com.example.savemoneytime.debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.savemoneytime.MainApplication.MainActivity;
import com.example.savemoneytime.database.AppDatabase;
import com.example.savemoneytime.model.ExpenseEntity;
import com.example.savemoneytime.model.RevenueEntity;
import java.util.Calendar;

/**
 * Debug-only: replaces all data with a realistic sample month, then opens the app.
 * adb shell am start -n com.example.savemoneytime/.debug.DemoSeedActivity
 */
public class DemoSeedActivity extends Activity {

    private static final String[][] EXPENSES = {
            // title, amount, category, days ago, hour
            {"Pho Thin", "9", "Food", "0", "12"},
            {"Grab to campus", "6", "Transport", "0", "8"},
            {"Rent", "650", "Housing", "1", "9"},
            {"Boba tea", "6", "Food", "1", "16"},
            {"Gym membership", "35", "Health", "2", "18"},
            {"Groceries", "84", "Food", "3", "19"},
            {"Movie night", "24", "Entertainment", "4", "21"},
            {"Phone plan", "30", "Phone", "5", "10"},
            {"Electricity", "58", "Utilities", "6", "11"},
            {"Textbooks", "120", "Education", "8", "14"},
            {"Sneakers", "95", "Shopping", "10", "17"},
            {"Coffee", "5", "Food", "11", "8"},
            {"Bus pass", "45", "Transport", "13", "9"},
            {"Pharmacy", "18", "Health", "15", "13"},
            {"Concert ticket", "60", "Entertainment", "18", "20"},
            {"Dinner with friends", "42", "Food", "21", "19"},
            {"Internet", "40", "Utilities", "35", "10"},
            {"Rent", "650", "Housing", "31", "9"},
            {"Groceries", "76", "Food", "38", "18"},
            {"Flight home", "320", "Travel", "60", "7"},
            {"Rent", "650", "Housing", "62", "9"},
            {"Groceries", "91", "Food", "66", "18"},
    };

    private static final String[][] REVENUES = {
            {"Salary", "2400", "Salary", "2", "9"},
            {"Freelance logo", "350", "Freelance", "9", "15"},
            {"Dividends", "42", "Investment", "16", "10"},
            {"Salary", "2400", "Salary", "32", "9"},
            {"Tutoring", "180", "Freelance", "40", "17"},
            {"Salary", "2400", "Salary", "62", "9"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(this);
        AppDatabase.dbExecutor.execute(() -> {
            db.clearAllTables();
            for (String[] e : EXPENSES) {
                db.expenseDao().insert(new ExpenseEntity(e[0], Long.parseLong(e[1]), e[2], when(e[3], e[4]), ""));
            }
            for (String[] r : REVENUES) {
                db.revenueDao().insert(new RevenueEntity(r[0], Long.parseLong(r[1]), r[2], when(r[3], r[4]), ""));
            }
            runOnUiThread(() -> {
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            });
        });
    }

    private static long when(String daysAgo, String hour) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -Integer.parseInt(daysAgo));
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        c.set(Calendar.MINUTE, 15);
        return c.getTimeInMillis();
    }
}
