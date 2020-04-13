package com.example.musicapp.DateBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.musicapp.DateBase.Data.DB_NAME;
import static com.example.musicapp.DateBase.Data.DB_VERSION;
import static com.example.musicapp.DateBase.Data.SQL_TB_PALYLIST;
import static com.example.musicapp.DateBase.Data.SQL_TB_SEARCHSESSION;

/**
 * Created by PC on 2019/6/7.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TB_PALYLIST);
        db.execSQL(SQL_TB_SEARCHSESSION);
        Log.v("database","创建表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }
}
