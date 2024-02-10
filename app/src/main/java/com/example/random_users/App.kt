package com.example.random_users

import android.app.Application
import androidx.room.Room
import com.example.random_users.api.apiModule
import com.example.random_users.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                mainModule,
                apiModule,
                database
            )
            androidContext(this@App)
            androidLogger()
        }
    }

    private val database = module {
        single {
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "users"
            ).build()
        }
        single {
            val database = get<AppDatabase>()
            database.userDao()
        }
    }
}