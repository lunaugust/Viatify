package com.grupoprominente.viatify.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.grupoprominente.viatify.model.Viatic;
import com.grupoprominente.viatify.model.Travel;

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
        db.execSQL(Travel.CREATE_TABLE);
        insertTravels(db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Viatic.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Travel.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public long insertViatic(String title, String description, Double amount, String currency, String path, int serviceLine, String timeStamp) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Viatic.COLUMN_TITLE, title);
        values.put(Viatic.COLUMN_DESCRIPTION, description);
        values.put(Viatic.COLUMN_AMOUNT, amount);
        values.put(Viatic.COLUMN_CURRENCY, currency);
        values.put(Viatic.COLUMN_IMGPATH, path);
        values.put(Viatic.COLUMN_SERVICELINE, serviceLine);
        values.put(Viatic.COLUMN_TIMESTAMP, timeStamp);

        // insert row
        long id = db.insert(Viatic.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public long insertTravel(String title) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Travel.COLUMN_TITLE, title);

        // insert row
        long id = db.insert(Travel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public Viatic getViatic(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Viatic.TABLE_NAME,
                new String[]{Viatic.COLUMN_ID, Viatic.COLUMN_TITLE, Viatic.COLUMN_DESCRIPTION, Viatic.COLUMN_AMOUNT, Viatic.COLUMN_CURRENCY, Viatic.COLUMN_TIMESTAMP, Viatic.COLUMN_IMGPATH, Viatic.COLUMN_SERVICELINE},
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
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_CURRENCY)),
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_IMGPATH)),
                cursor.getInt(cursor.getColumnIndex(Viatic.COLUMN_SERVICELINE)));

        // close the db connection
        cursor.close();

        return viatic;
    }
    public Travel getTravel(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Travel.TABLE_NAME,
                new String[]{Travel.COLUMN_ID, Travel.COLUMN_TITLE},
                Viatic.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Travel travel = new Travel(
                cursor.getInt(cursor.getColumnIndex(Travel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Travel.COLUMN_TITLE)));

        // close the db connection
        cursor.close();

        return travel;
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
                viatic.setId(cursor.getInt(cursor.getColumnIndex(Viatic.COLUMN_ID)));
                viatic.setTitle(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_TITLE)));
                viatic.setDescription(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_DESCRIPTION)));
                viatic.setAmount(cursor.getDouble(cursor.getColumnIndex(Viatic.COLUMN_AMOUNT)));
                viatic.setCurrency(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_CURRENCY)));
                viatic.setTimestamp(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_TIMESTAMP)));
                viatic.setImgpath(cursor.getString(cursor.getColumnIndex(Viatic.COLUMN_IMGPATH)));
                viatic.setServiceline(cursor.getInt(cursor.getColumnIndex(Viatic.COLUMN_SERVICELINE)));

                viatics.add(viatic);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return viatics;
    }
    public List<Travel> getAllTravel() {
        List<Travel> travels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Travel.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Travel travel = new Travel();
                travel.setId(cursor.getInt(cursor.getColumnIndex(travel.COLUMN_ID)));
                travel.setTitle(cursor.getString(cursor.getColumnIndex(travel.COLUMN_TITLE)));
                travels.add(travel);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return travels;
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
    public int getTravelsCount() {
        String countQuery = "SELECT  * FROM " + Travel.TABLE_NAME;
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
        values.put(viatic.COLUMN_CURRENCY, viatic.getCurrency());
        values.put(viatic.COLUMN_IMGPATH, viatic.getImgpath());
        values.put(viatic.COLUMN_SERVICELINE, viatic.getServiceline());
        values.put(viatic.COLUMN_TIMESTAMP, viatic.getTimestamp());

        // updating row
        return db.update(viatic.TABLE_NAME, values, viatic.COLUMN_ID + " = ?",
                new String[]{String.valueOf(viatic.getId())});
    }
    public int updateTravel(Travel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(travel.COLUMN_TITLE, travel.getTitle());

        // updating row
        return db.update(travel.TABLE_NAME, values, travel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(travel.getId())});
    }

    public void deleteViatic(Viatic viatic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(viatic.TABLE_NAME, viatic.COLUMN_ID + " = ?",
                new String[]{String.valueOf(viatic.getId())});
        db.close();
    }

    public void deleteViaticById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Viatic.TABLE_NAME, Viatic.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTravel(Travel travel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(travel.TABLE_NAME, travel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(travel.getId())});
        db.close();
    }

    public void insertTravels(SQLiteDatabase db){
        String[] arrayOrg = {"Viaje 1", "Viaje 2", "Viaje 3", "Viaje 4"};
        for (String title : arrayOrg) {
            ContentValues values = new ContentValues();

            values.put(Travel.COLUMN_TITLE, title);

            db.insert(Travel.TABLE_NAME, null, values);

        }
    }

}
