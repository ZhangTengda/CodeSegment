package com.xnjz.one.room2

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 */
@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
}