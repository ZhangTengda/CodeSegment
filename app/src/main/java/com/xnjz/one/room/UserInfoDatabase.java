package com.xnjz.one.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * 数据库
 */
@Database(entities = UserInfoBean.class, version = 1, exportSchema = false)
public abstract class UserInfoDatabase extends RoomDatabase {

    public abstract UserInfoDao userInfoDao();

    public UserInfoDatabase() {
    }

    private static UserInfoDatabase userInfoDatabase = null;

    public static UserInfoDatabase getInstance(Context mContext) {
        if (userInfoDatabase == null) {
            synchronized (UserInfoDatabase.class) {
                userInfoDatabase = Room.databaseBuilder(mContext.getApplicationContext(),
                                UserInfoDatabase.class,
                                "user_database")
//                        .enableMultiInstanceInvalidation() // 应用在多个进程中运行
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return userInfoDatabase;
    }
}
