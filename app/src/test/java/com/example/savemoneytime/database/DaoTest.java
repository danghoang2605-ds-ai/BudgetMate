package com.example.savemoneytime.database;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.savemoneytime.model.ExpenseEntity;
import com.example.savemoneytime.model.RevenueEntity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/** Runs the real Room DAOs on an in-memory SQLite database (Robolectric, no emulator). */
@RunWith(AndroidJUnit4.class)
@Config(sdk = 33)
public class DaoTest {

    private AppDatabase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void tearDown() {
        db.close();
    }

    private static long at(int year, int month, int day, int hour) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month - 1, day, hour, 0, 0);
        return c.getTimeInMillis();
    }

    @Test
    public void allTimeTotalsSumEveryMonth() {
        db.expenseDao().insert(new ExpenseEntity("Pho", 5, "Food", at(2026, 1, 10, 12), ""));
        db.expenseDao().insert(new ExpenseEntity("Rent", 700, "Housing", at(2026, 2, 1, 9), ""));
        db.revenueDao().insert(new RevenueEntity("Salary", 2000, "Salary", at(2026, 2, 28, 9), ""));
        assertEquals(705, db.expenseDao().getTotalAll());
        assertEquals(2000, db.revenueDao().getTotalAll());
    }

    @Test
    public void monthFilterUsesDeviceLocalTime() {
        // 23:30 local on the last day of the month must stay in that month.
        long lateNight = at(2026, 3, 31, 23) + 30 * 60_000L;
        db.expenseDao().insert(new ExpenseEntity("Late snack", 7, "Food", lateNight, ""));
        String month = new SimpleDateFormat("MM", Locale.US).format(lateNight);
        String year = new SimpleDateFormat("yyyy", Locale.US).format(lateNight);
        assertEquals(1, db.expenseDao().getByMonth(month, year).size());
        assertEquals(7, db.expenseDao().getTotalByMonth(month, year));
    }

    @Test
    public void deleteById() {
        db.expenseDao().insert(new ExpenseEntity("Coffee", 3, "Food", at(2026, 5, 5, 8), ""));
        int id = db.expenseDao().getAll().get(0).getId();
        db.expenseDao().deleteById(id);
        assertEquals(0, db.expenseDao().getAll().size());
    }
}
