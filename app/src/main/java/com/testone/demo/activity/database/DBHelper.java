package com.testone.demo.activity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // 数据库文件名
    private static final String DB_NAME = "my_database.db";
    // 数据库表名
    public static final String TABLE_NAME = "t_person";
    // 数据库版本号
    public static final int DB_VERSION = 1;

    public static final String NAME = "name";
    public static final String AGE = "age";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建表
        String sql = "create table " +
                TABLE_NAME +
                "(_id integer primary key autoincrement, " +
                NAME + " varchar, " +
                AGE + " varchar"
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
