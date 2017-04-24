package com.gregttn.datastorage.stores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.gregttn.datastorage.stores.Database.DatabaseStoreContract.KeyValueEntry.*;

public class Database extends SQLiteOpenHelper implements DataStore {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SampleDatabase.db";
    private static final String ENTRY_KEY = "sample_key";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void save(String data) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(VALUE_COLUMN, data);
        contentValues.put(KEY_COLUMN, ENTRY_KEY);

        db.insertWithOnConflict(TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public String retrieve() {
        SQLiteDatabase db = getReadableDatabase();
        String[] queryColumns = { VALUE_COLUMN };
        String[] whereValues = { ENTRY_KEY };
        String selection = KEY_COLUMN + " = ?";

        Cursor cursor = db.query(TABLE, queryColumns, selection, whereValues, null, null, null);

        if(cursor.moveToFirst()) {
            int valueColumnIndex = cursor.getColumnIndexOrThrow(VALUE_COLUMN);
            String value = cursor.getString(valueColumnIndex);
            cursor.close();

            return value;
        }

        return "";
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    class DatabaseStoreContract {
        private DatabaseStoreContract() {}

        class KeyValueEntry implements BaseColumns {
            public static final String TABLE = "entry";
            public static final String VALUE_COLUMN = "value";
            public static final String KEY_COLUMN = "key";

            static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE + "(" +
                    KEY_COLUMN + " TEXT PRIMARY KEY, " +
                    VALUE_COLUMN + " TEXT" +
                    ")";

            static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
        }
    }
}
