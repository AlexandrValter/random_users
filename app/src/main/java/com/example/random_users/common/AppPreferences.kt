package com.example.random_users.common

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val AMOUNT_USERS = "10"
    }

    var amountUsers: Int
        get() = sharedPreferences.getInt(AMOUNT_USERS, 10)
        set(value) = sharedPreferences.edit().putInt(AMOUNT_USERS, value).apply()
}