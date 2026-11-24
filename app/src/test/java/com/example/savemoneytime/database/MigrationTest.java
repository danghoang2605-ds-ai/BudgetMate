package com.example.savemoneytime.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.savemoneytime.model.ExpenseEntity;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * Builds a database exactly as schema v1 shipped, then opens it with the current app code.
 * Room validates the migrated schema on open, so a wrong migration fails this test.
 */
@RunWith(AndroidJUnit4.class)
@Config(sdk = 33)
public class MigrationTest {

    @Test
    public void v1DataSurvivesUpgradeAndGetsIndexes() {
        Context context = ApplicationProvider.getApplicationContext();
        File file = context.getDatabasePath("migration-test.db");
        file.getParentFile().mkdirs();
        file.delete();

        SQLiteDatabase v1 = SQLiteDatabase.openOrCreateDatabase(file, null);
        v1.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "`title` TEXT, `amount` INTEGER NOT NULL, `category_name` TEXT, `date` INTEGER NOT NULL, `note` TEXT)");
        v1.execSQL("CREATE TABLE IF NOT EXISTS `revenues` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "`title` TEXT, `amount` INTEGER NOT NULL, `category_name` TEXT, `date` INTEGER NOT NULL, `note` TEXT)");
        v1.execSQL("INSERT INTO expenses (title, amount, category_name, date, note) VALUES ('Boba', 6, 'Food', 1767225600000, '')");
        v1.setVersion(1);
        v1.close();

        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, file.getAbsolutePath())
                .addMigrations(Migrations.ALL)
                .allowMainThreadQueries()
                .build();
        try {
            List<ExpenseEntity> rows = db.expenseDao().getAll();
            assertEquals(1, rows.size());
            assertEquals("Boba", rows.get(0).getTitle());

            Cursor c = db.getOpenHelper().getReadableDatabase()
                    .query("SELECT name FROM sqlite_master WHERE type = 'index' AND name LIKE 'index_%_date'");
            int count = c.getCount();
            c.close();
            assertTrue("date indexes created", count == 2);
        } finally {
            db.close();
            file.delete();
        }
    }
}
