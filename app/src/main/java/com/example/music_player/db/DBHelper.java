package com.example.music_player.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "login.db";
//    private static String TABLE_NAME = "favoriteTable";
//    private static String KEY_ID = "id";
//    private static String DEFAULT_TRANSLATION = "defaultTranslation";
//    private static String MIWOK_TRANSLATION = "miwokTranslation";
//    private static String ITEM_IMAGE = "imageResourceId";
//    private static String ITEM_AUDIO = "audioResourceId";
//    private static String FAVORITE_STATUS = "fStatus";

    public DBHelper(Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(email TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    public boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result == -1)
            return false;
        else
        return true;
    }

    public boolean checkUsername(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email = ?"
                , new String[] {email});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email = ? and password = ?"
                , new String[] {email, password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
