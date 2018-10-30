package com.grupoprominente.viatify.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.grupoprominente.viatify.model.ServiceLines;
import com.grupoprominente.viatify.model.SubOrganizations;
import com.grupoprominente.viatify.model.Viatic;
import com.grupoprominente.viatify.model.Travel;
import com.grupoprominente.viatify.model.Organizations;

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
        db.execSQL(Organizations.CREATE_TABLE);
        db.execSQL(SubOrganizations.CREATE_TABLE);
        db.execSQL(ServiceLines.CREATE_TABLE);
        insertOrganizations(db);
        insertSubOrganizations(db);
        insertServiceLines(db);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Viatic.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Travel.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Organizations.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubOrganizations.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ServiceLines.TABLE_NAME);
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
    public long insertTravel(String title, String description, String path) { //Double amount,
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Travel.COLUMN_TITLE, title);
        values.put(Travel.COLUMN_DESCRIPTION, description);
       // values.put(Viatic.COLUMN_AMOUNT, amount);
        values.put(Travel.COLUMN_IMGPATH, path);

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
    public Travel getTravel(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Travel.TABLE_NAME,
                new String[]{Travel.COLUMN_ID, Travel.COLUMN_TITLE, Travel.COLUMN_DESCRIPTION,  Travel.COLUMN_TIMESTAMP, Travel.COLUMN_IMGPATH}, //Viatic.COLUMN_AMOUNT,
                Viatic.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Travel travel = new Travel(
                cursor.getInt(cursor.getColumnIndex(Travel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Travel.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Travel.COLUMN_DESCRIPTION)),
                //cursor.getDouble(cursor.getColumnIndex(Viatic.COLUMN_AMOUNT)),
                cursor.getString(cursor.getColumnIndex(Travel.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Travel.COLUMN_IMGPATH)));

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
    public List<Travel> getAllTravel() {
        List<Travel> travels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Travel.TABLE_NAME + " ORDER BY " +
                Travel.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Travel travel = new Travel();
                travel.setId(cursor.getInt(cursor.getColumnIndex(travel.COLUMN_ID)));
                travel.setTitle(cursor.getString(cursor.getColumnIndex(travel.COLUMN_TITLE)));
                travel.setDescription(cursor.getString(cursor.getColumnIndex(travel.COLUMN_DESCRIPTION)));
               // viatic.setAmount(cursor.getDouble(cursor.getColumnIndex(viatic.COLUMN_AMOUNT)));
                travel.setTimestamp(cursor.getString(cursor.getColumnIndex(travel.COLUMN_TIMESTAMP)));
                travel.setImgpath(cursor.getString(cursor.getColumnIndex(travel.COLUMN_IMGPATH)));

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
        values.put(viatic.COLUMN_IMGPATH, viatic.getImgpath());

        // updating row
        return db.update(viatic.TABLE_NAME, values, viatic.COLUMN_ID + " = ?",
                new String[]{String.valueOf(viatic.getId())});
    }
    public int updateTravel(Travel travel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(travel.COLUMN_TITLE, travel.getTitle());
        values.put(travel.COLUMN_DESCRIPTION, travel.getDescription());
        //values.put(viatic.COLUMN_AMOUNT, viatic.getAmount());
        values.put(travel.COLUMN_IMGPATH, travel.getImgpath());

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
    public void deleteTravel(Travel travel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(travel.TABLE_NAME, travel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(travel.getId())});
        db.close();
    }

    private void insertOrganizations(SQLiteDatabase db)
    {
        String[] arrayOrg = {"Aplicaciones","Infraestructura","PMO","Tecnología","Comercial","Back Office","RRHH","Mantenimiento","Gerencia General","Presidencia"};
        Integer id = 1;
        for (String description: arrayOrg) {

            ContentValues values = new ContentValues();

            values.put(Organizations.COLUMN_ID, id);
            values.put(Organizations.COLUMN_TITLE, description);

            db.insert(Organizations.TABLE_NAME, null, values);
            id++;
        }

    }

    public List<Organizations> getAllOrganizations() {

        List<Organizations> organizations = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Organizations.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Organizations organization = new Organizations();
                organization.setId(cursor.getInt(cursor.getColumnIndex(Organizations.COLUMN_ID)));
                organization.setTitle(cursor.getString(cursor.getColumnIndex(Organizations.COLUMN_TITLE)));
                organizations.add(organization);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return organizations;
    }

    private void insertSubOrganizations(SQLiteDatabase db)
    {
        HashMap<String, Integer> dic_SubOrgs = new HashMap<>();
        dic_SubOrgs.put("Solucion de sw./Producto",1);
        dic_SubOrgs.put("Servicio al Cliente/Soporte de Aplicaciones",1);
        dic_SubOrgs.put("Proyecto de SW./ Desarrollo de Aplicaciones",1);
        dic_SubOrgs.put("Monitoreo/MDA",2);
        dic_SubOrgs.put("Operaciones de Servicio - DATA CENTER",2);
        dic_SubOrgs.put("Soporte Tecnico - Infraestructura",2);
        dic_SubOrgs.put("Planificacion y Asesoramiento - Servicio Infraestructura",2);
        dic_SubOrgs.put("Calidad/PMO",3);
        dic_SubOrgs.put("OFICINA DE PROYECTOS",3);
        dic_SubOrgs.put("BRA",4);
        dic_SubOrgs.put("BRH",4);
        dic_SubOrgs.put("MTV",4);
        dic_SubOrgs.put("CORREDORES",4);
        dic_SubOrgs.put("BCyL/BRD/SOFSE",4);
        dic_SubOrgs.put("MONEDERO",4);
        dic_SubOrgs.put("Preventa",4);
        dic_SubOrgs.put("TRADITUM",4);
        dic_SubOrgs.put("GESTION DE PROCESO DE NEGOCIOS",4);
        dic_SubOrgs.put("INGENIERIA INFORMATICA",4);
        dic_SubOrgs.put("Ventas",5);
        dic_SubOrgs.put("MKT",5);
        dic_SubOrgs.put("RRII",5);
        dic_SubOrgs.put("PREVENTA",5);
        dic_SubOrgs.put("Adm. Y Fin.",6);
        dic_SubOrgs.put("Adm. De ventas y contrataciones",6);
        dic_SubOrgs.put("RECURSOS HUMANOS",7);
        dic_SubOrgs.put("Cordoba",8);
        dic_SubOrgs.put("Buenos Aires",8);
        dic_SubOrgs.put("Gerencia General",9);
        dic_SubOrgs.put("Presidencia",10);

        Iterator it = dic_SubOrgs.entrySet().iterator();
        Integer id = 1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ContentValues values = new ContentValues();
            values.put(SubOrganizations.COLUMN_ID, id);
            values.put(SubOrganizations.COLUMN_TITLE, pair.getKey().toString());
            values.put(SubOrganizations.COLUMN_ORG_ID,  (Integer) pair.getValue());

            db.insert(SubOrganizations.TABLE_NAME, null, values);
            id++;
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    public List<SubOrganizations> getAllSubOrganizations() {

        List<SubOrganizations> subOrganizations = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubOrganizations.TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubOrganizations subOrganization = new SubOrganizations();
                subOrganization.setId(cursor.getInt(cursor.getColumnIndex(SubOrganizations.COLUMN_ID)));
                subOrganization.setTitle(cursor.getString(cursor.getColumnIndex(SubOrganizations.COLUMN_TITLE)));
                subOrganization.setOrg_id(cursor.getInt(cursor.getColumnIndex(SubOrganizations.COLUMN_ORG_ID)));
                subOrganizations.add(subOrganization);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return subOrganizations;
    }

    private void insertServiceLines(SQLiteDatabase db)
    {
        HashMap<String, Integer> dic_ServiceLines = new HashMap<>();
        dic_ServiceLines.put("Petra BPM",1);
        dic_ServiceLines.put("Hub",1);
        dic_ServiceLines.put("Mr Bubo",1);
        dic_ServiceLines.put("PREM",1);
        dic_ServiceLines.put("Consultoria BI",2);
        dic_ServiceLines.put("Consultoria de Pectra BPM",2);
        dic_ServiceLines.put("Consultoria de PREM",2);
        dic_ServiceLines.put("Consultoria de Mr. Bubo",2);
        dic_ServiceLines.put("Consultoria de O365",2);
        dic_ServiceLines.put("Analista Funcional",2);
        dic_ServiceLines.put("Mant SW.",2);
        dic_ServiceLines.put("Proyecto de SW.",3);
        dic_ServiceLines.put("Monitoreo de Servicios",4);
        dic_ServiceLines.put("Mesa de Ayuda",4);
        dic_ServiceLines.put("Servicio de Data Center",5);
        dic_ServiceLines.put("Consultoria de Infraestructura",5);
        dic_ServiceLines.put("Soporte Tecnico",6);
        dic_ServiceLines.put("Servicios Profesionales On Site",6);
        dic_ServiceLines.put("Gestion de Proyectos y Servicios",8);
        dic_ServiceLines.put("Gestion de Outsorcing",10);
        dic_ServiceLines.put("Gestion de Outsorcing",11);
        dic_ServiceLines.put("Gestion de Outsorcing",12);
        dic_ServiceLines.put("Gestion de Outsorcing",13);
        dic_ServiceLines.put("Gestion de Outsorcing",14);
        dic_ServiceLines.put("Gestion de Outsorcing",15);
        dic_ServiceLines.put("Gestion de Outsorcing",16);
        dic_ServiceLines.put("Gestion de Outsorcing",17);
        dic_ServiceLines.put("Gestion de Outsorcing",18);
        dic_ServiceLines.put("Gestion de Outsorcing",19);
        dic_ServiceLines.put("Ventas",20);
        dic_ServiceLines.put("MKT",21);
        dic_ServiceLines.put("RRII",22);
        dic_ServiceLines.put("Admin. y Fin.",24);
        dic_ServiceLines.put("Adm. De ventas y contrataciones",25);
        dic_ServiceLines.put("RECURSOS HUMANOS",26);
        dic_ServiceLines.put("Cordoba",27);
        dic_ServiceLines.put("Buenos Aires",28);
        dic_ServiceLines.put("Gerencia General",29);
        dic_ServiceLines.put("Presidencia",30);

        Iterator it = dic_ServiceLines.entrySet().iterator();
        Integer id = 1;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ContentValues values = new ContentValues();

            values.put(ServiceLines.COLUMN_ID, id);
            values.put(ServiceLines.COLUMN_TITLE, pair.getKey().toString());
            values.put(ServiceLines.COLUMN_SUB_ORG_ID, (Integer) pair.getValue());

            db.insert(ServiceLines.TABLE_NAME, null, values);
            id++;
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public List<ServiceLines> getAllServiceLines() {

        List<ServiceLines> serviceLines = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ServiceLines.TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ServiceLines serviceLine = new ServiceLines();
                serviceLine.setId(cursor.getInt(cursor.getColumnIndex(ServiceLines.COLUMN_ID)));
                serviceLine.setTitle(cursor.getString(cursor.getColumnIndex(ServiceLines.COLUMN_TITLE)));
                serviceLine.setSub_org_id(cursor.getInt(cursor.getColumnIndex(ServiceLines.COLUMN_SUB_ORG_ID)));
                serviceLines.add(serviceLine);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return serviceLines;
    }


}
