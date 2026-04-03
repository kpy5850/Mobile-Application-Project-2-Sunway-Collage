package com.example.genshintodolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor fetchActive() {
        String[] columns = new String[] {
                DatabaseHelper._ID,
                DatabaseHelper.TITLE,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.DATE,
                DatabaseHelper.DONE,
                DatabaseHelper.IS_DELETED
        };
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                DatabaseHelper.IS_DELETED + " = 0",
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchDeleted(){
        String[] columns = new String[]{
                DatabaseHelper._ID,
                DatabaseHelper.TITLE,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.DATE,
                DatabaseHelper.DONE,
                DatabaseHelper.IS_DELETED
        };
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME,
                columns,
                DatabaseHelper.IS_DELETED + " =1",
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchCompletedTasks(){
        String[] columns = new String[]{
                DatabaseHelper._ID,
                DatabaseHelper.TITLE,
                DatabaseHelper.DESCRIPTION,
                DatabaseHelper.DATE,
                DatabaseHelper.DONE,
                DatabaseHelper.IS_DELETED
        };

        Cursor cursor = database.query(
                DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.DONE + " = 1 AND " + DatabaseHelper.IS_DELETED + " = 1",
                null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchTitlesForDate(String date){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT DISTINCT listTitle FROM TODOLIST WHERE taskDate = ?", new String[]{date});
    }

    public void insert(String title, String description, String date) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.TITLE, title);
        cv.put(DatabaseHelper.DESCRIPTION, description);
        cv.put(DatabaseHelper.DONE, 0);
        cv.put(DatabaseHelper.DATE, date);
        cv.put(DatabaseHelper.IS_DELETED, 0);
        database.insert(DatabaseHelper.TABLE_NAME, null, cv);
    }

    public void update(int id, String title, String description, String date) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.TITLE, title);
        cv.put(DatabaseHelper.DESCRIPTION, description);
        cv.put(DatabaseHelper.DATE, date);
        database.update(DatabaseHelper.TABLE_NAME, cv, DatabaseHelper._ID + " =?", new String[]{String.valueOf(id)});
    }

    public void updateDone(int id, boolean done){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.DONE, done ? 1 : 0);
        database.update(DatabaseHelper.TABLE_NAME, cv, DatabaseHelper._ID + "=?", new String[]{ String.valueOf(id)});
    }

    public void softDelete(int id) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.IS_DELETED, 1);
        database.update(DatabaseHelper.TABLE_NAME, cv, DatabaseHelper._ID + "=?", new String[]{String.valueOf(id)});
    }
}
