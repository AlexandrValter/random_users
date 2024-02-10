package com.example.random_users.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query ("SELECT * FROM UserDb")
    fun getAll(): List<UserDb>

    @Query ("SELECT * FROM UserDb WHERE uid = :id")
    fun getUser(id: Int): UserDb

    @Insert
    fun insertAll(users: List<UserDb>)

    @Query("DELETE FROM UserDb")
    fun clearAll()

    @Query("SELECT uid FROM UserDb WHERE first_name = :firstName AND last_name = :lastName AND phone = :phone")
    fun findUserId(firstName: String, lastName: String, phone: String): Int?
}