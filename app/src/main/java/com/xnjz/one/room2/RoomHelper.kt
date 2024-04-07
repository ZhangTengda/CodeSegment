package com.xnjz.one.room2

import androidx.room.Room
import com.xnjz.one.base.MyApp

/**
 * RoomHelper
 */
object RoomHelper {

    private val appDatabase by lazy {
        MyApp.getInstance()
            ?.let { Room.databaseBuilder(it, AppDatabase::class.java, "database_coal").build() }
    }

    private val readHistoryDao by lazy { appDatabase?.readHistoryDao() }

}