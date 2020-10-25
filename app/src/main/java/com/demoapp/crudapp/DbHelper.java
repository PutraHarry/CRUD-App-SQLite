package com.demoapp.crudapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

// kelas ini berisi kode untuk melakukan manipulasi CRUD dengan menggunakan SQLite
public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "appnote.db";
    public static final String TABLE_SQLite = "sqlite";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Perintah yang dijalankan pada saat aplikasi dijalankan pertama kali dan akan membuat database baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL " +
                " )";
        db.execSQL(SQL_CREATE_NOTE_TABLE);
    }

    //Perintah yang dijalankan pada saat aplikasi memiliki versi baru dalam skema database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    // Kode arraylist yang akan menyimpan data seccara array list, lalu cursor akan menaruh data array tadi sesuai dengan index kolomnya dengan kondisi dan looping didalamnya. Pada dasarnya kode ini akan memperlihatkan keseluruhan data arraylist
    public ArrayList<HashMap<String, String>> getAllData(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " +TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAME, cursor.getString(1));
                map.put(COLUMN_ADDRESS, cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);
        database.close();
        return wordList;
    }

    // Kode untuk melakukan insert kedalam database dengan menggunakan query
    public void insert(String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLite + " (name, address) " + " VALUES ('" + name + "', '" + address + "')";
        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    //Kode untuk melakukan update
    public void update(int id, String name, String address) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_SQLite + " SET "+ COLUMN_NAME + "='" + name + "', "+ COLUMN_ADDRESS + "='" + address + "'"+ " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    //kode untuk melakukan delete
    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DELETE FROM " + TABLE_SQLite + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
