package com.example.ausias.intercibus.basedades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ausias.intercibus.classes.Usuari;

import java.util.ArrayList;
import java.util.List;

/**
 * Aquesta classe serveix per crear o actualitzar la base de dades i obtenir informació d'aquesta
 * @author Antonio de Dios Durán
 * @version 1.0
 */

public class BBDDHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuaris.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    FeedReaderContract.FeedEntry.COLUMN_USERNAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_PASSWORD + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    /**
     * Constructor de la classe BBDDHelper
     * @param context Paràmetre que defineix el context que tindra la classe
     */
    public BBDDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Mètode que executa la sentència sql per fer la creació de la taula
     * @param db Paràmetre que fa referència a la base de dades sqlite
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Mètode que executa la sentència sql per eliminar les taules i crear una de noves
     * @param db Paràmetre que fa referència a la base de dades sqlite
     * @param i Paràmetre que fa referència a la versió anterior de la base de dades
     * @param i1 Paràmetre que fa referència a la versió nova de la base de dades
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Mètode per comprovar si un usuari està guardat o no ho està
     * @param username Paràmetre que fa referència al username del usuari
     * @param password Paràmetre que fa referència al password del usuari
     * @return Un booleà indicant si existeix o no l'usuari
     */
    public boolean usuariRegistrat (String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {};

        String selection =
                FeedReaderContract.FeedEntry.COLUMN_USERNAME + " = ? AND " + FeedReaderContract.FeedEntry.COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = { username, password };


        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // The table to query
                projection,                                 // The columns to return
                selection,                                  // The columns for the WHERE clause
                selectionArgs,                              // The values for the WHERE clause
                null,                              // don't group the rows
                null,                               // don't filter by row groups
                null                               // The sort order
        );

        while(cursor.moveToNext()) {
            return true;
        }

        return false;
    }

    /**
     * Mètode que retorna l'usuari i el password
     * @return Usuari i password si existeix a la base de dades
     */
    public List<String> getUsuari () {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {FeedReaderContract.FeedEntry.COLUMN_USERNAME, FeedReaderContract.FeedEntry.COLUMN_PASSWORD};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // The table to query
                projection,                                 // The columns to return
                null,                                  // The columns for the WHERE clause
                null,                              // The values for the WHERE clause
                null,                              // don't group the rows
                null,                               // don't filter by row groups
                null                               // The sort order
        );

        boolean trobat = false;
        List<String> usuari = new ArrayList<>();
        while(cursor.moveToNext() && !trobat) {
            usuari.add(cursor.getString(0));
            usuari.add(cursor.getString(1));
            trobat = true;
        }
        cursor.close();

        if (trobat) {
            return usuari;
        }

        return null;
    }

    /**
     * Mètode que retorna si la BBDD té contingut (hi ha un usuari guardat)
     * @return Un enter que fa referència al de files que retorna la base de dades
     */
    public int teContingut () {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {FeedReaderContract.FeedEntry.COLUMN_USERNAME};

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // The table to query
                projection,                                 // The columns to return
                null,                                  // The columns for the WHERE clause
                null,                              // The values for the WHERE clause
                null,                              // don't group the rows
                null,                               // don't filter by row groups
                null                               // The sort order
        );

        // Comptar les files que té la taula
        int comptador = 0;
        while(cursor.moveToNext()) {
            comptador ++;
        }
        cursor.close();

        return comptador;
    }

    /**
     * Mètode que guarda dades a la BBDD (Usuari)
     * @param username Paràmetre que fa referència al username del usuari
     * @param password Paràmetre que fa referència al password del usuari
     */
    public void guarda(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_USERNAME, username);
        values.put(FeedReaderContract.FeedEntry.COLUMN_PASSWORD, password);

        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

}
