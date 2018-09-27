package com.grupoprominente.viatify.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.grupoprominente.viatify.model.Viatic;

public class DatabaseHelper  extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Viatics_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Viatic.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Viatic.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long insertViatic(String title, String description, Double amount, String path) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Viatic.COLUMN_TITLE, title);
        values.put(Viatic.COLUMN_DESCRIPTION, description);
        values.put(Viatic.COLUMN_AMOUNT, amount);
        values.put(Viatic.COLUMN_IMGPATH, path);

        // insert row
        long id = db.insert(Viatic.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public Viatic getViatic(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Viatic.TABLE_NAME,
                new String[]{Viatic.COLUMN_ID, Viatic.COLUMN_TITLE, Viatic.COLUMN_DESCRIPTION, Viatic.COLUMN_AMOUNT, Viatic.COLUMN_TIMESTAMP, Viatic.COLUMN_IMGPATH},
                Viatic.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Viatic viatic = new Viatic(
                cursor.getInt(cursor.getColumnIndex(Viatic.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_DESCRIPTION)),
                cursor.getDouble(cursor.getColumnIndex(Viatic.COLUMN_AMOUNT)),
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_IMGPATH)));

        // close the db connection
        cursor.close();

        return viatic;
    }

    public List<Viatic> getAllViatics() {
        List<Viatic> viatics = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Viatic.TABLE_NAME + " ORDER BY " +
                Viatic.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Viatic viatic = new Viatic();
                viatic.setId(cursor.getInt(cursor.getColumnIndex(viatic.COLUMN_ID)));
                viatic.setTitle(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_TITLE)));
                viatic.setDescription(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_DESCRIPTION)));
                viatic.setAmount(cursor.getDouble(cursor.getColumnIndex(viatic.COLUMN_AMOUNT)));
                viatic.setTimestamp(cursor.getString(cursor.getColumnIndex(viatic.COLUMN_TIMESTAMP)));
                viatic.setImgpath(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_IMGPATH)));

                viatics.add(viatic);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return viatics;
    }

    public int getViaticsCount() {
        String countQuery = "SELECT  * FROM " + Viatic.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }
    public int updateViatic(Viatic viatic) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(viatic.COLUMN_TITLE, viatic.getTitle());
        values.put(viatic.COLUMN_DESCRIPTION, viatic.getDescription());
        values.put(viatic.COLUMN_AMOUNT, viatic.getAmount());
        values.put(viatic.COLUMN_IMGPATH, viatic.getImgpath());

        // updating row
        return db.update(viatic.TABLE_NAME, values, viatic.COLUMN_ID + " = ?",
                new String[]{String.valueOf(viatic.getId())});
    }
    public void deleteViatic(Viatic viatic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(viatic.TABLE_NAME, viatic.COLUMN_ID + " = ?",
                new String[]{String.valueOf(viatic.getId())});
        db.close();
    }
}
