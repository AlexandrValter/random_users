package com.example.random_users.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}