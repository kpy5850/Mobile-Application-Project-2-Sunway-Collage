package com.example.genshintodolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "TODOLIST";

    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "listTitle";
    public static final String DESCRIPTION = "listDescription";
    public static final String DATE = "taskDate";
    public static final String DONE = "done";
    public static final String IS_DELETED = "is_deleted";

    // Database Information
    static final String DB_NAME = "GenshinTask.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITLE + " TEXT NOT NULL, " +
            DESCRIPTION + " TEXT, " +
            DATE + " TEXT, " +
            DONE + " INTEGER DEFAULT 0, " +
            IS_DELETED + " INTEGER DEFAULT 0" + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

