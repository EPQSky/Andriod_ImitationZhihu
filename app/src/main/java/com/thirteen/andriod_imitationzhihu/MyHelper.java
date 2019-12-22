package com.thirteen.andriod_imitationzhihu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context){
        super(context, "my.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user(_id integer primary key autoincrement, name varchar(24), password varchar(48), be boolean)");
        sqLiteDatabase.execSQL("create table question(_id integer primary key autoincrement, title varchar(128), description text, creator varchar(24), gmt_create data)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
