package com.example.savemoneytime.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Schema history. Bump {@link AppDatabase} version, add a Migration here and list it in ALL;
 * the exported schemas in app/schemas/ show exactly what changed between versions.
 */
public final class Migrations {

    private Migrations() {}

    /** v2: index the date column used by every month filter and sort. */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_date` ON `expenses` (`date`)");
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_revenues_date` ON `revenues` (`date`)");
        }
    };

    public static final Migration[] ALL = { MIGRATION_1_2 };
}
