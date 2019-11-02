package com.example.berkh.wiki_proje;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqllite_database_sepet2";

    private static final String TABLE_NAME = "urunler";
    private static String Urun_adi = "urun_adi";
    private static String Urun_ıd = "urun_id";
    private static String Urun_fiyat = "urun_fiyat";
    private static String Urun_barcode = "urun_barcode";
    private static String Urun_adet = "urun_adet";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + Urun_ıd + " INTEGER PRIMARY KEY AUTOINCREMENT," + Urun_adi + " TEXT," + Urun_fiyat + " TEXT," +
                Urun_barcode + " TEXT," + Urun_adet +" TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void ürünSil(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Urun_ıd + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void urunEkle(String urun_adi, String urun_id, String urun_fiyat, String urun_barcode, String urun_adet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Urun_adi, urun_adi);
        values.put(Urun_ıd, urun_id);
        values.put(Urun_fiyat, urun_fiyat);
        values.put(Urun_barcode, urun_barcode);
        values.put(Urun_adet, urun_adet);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public ArrayList<HashMap<String, String>> urunler() {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> urunlistesi = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                urunlistesi.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return kitap liste
        return urunlistesi;
    }


    public int getRowCount() {
        //Login uygulamasında kullanacağız
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // If you need to add a new column
        if (arg2 > arg1) {
            arg0.execSQL("ALTER TABLE TABLE_NAME ADD COLUMN Urun_adet TEXT");
        }

    }
}