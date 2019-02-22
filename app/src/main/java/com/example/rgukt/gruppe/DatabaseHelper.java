package com.example.rgukt.gruppe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "images.db";
    public String query = "CREATE TABLE LIKEDIMAGES(IMAGE TEXT,TIMESTAMP TEXT);";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LIKEDIMAGES;");
        onCreate(db);
    }

    public long insertData(String address,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IMAGE",address);
        contentValues.put("TIMESTAMP",time);
        long result = db.insert("LIKEDIMAGES",null ,contentValues);
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM LIKEDIMAGES",null);
        return result;
    }
}
