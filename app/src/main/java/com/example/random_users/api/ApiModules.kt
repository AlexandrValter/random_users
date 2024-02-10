package com.example.random_users.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val moshi: Moshi = Moshi.Builder()
    .build()

val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
    addNetworkInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    pingInterval(10, TimeUnit.SECONDS)
    connectTimeout(2, TimeUnit.MINUTES)
    writeTimeout(60, TimeUnit.SECONDS)
    readTimeout(60, TimeUnit.SECONDS)
    connectionPool(ConnectionPool(10, 1, TimeUnit.MINUTES))
}.build()

val apiModule = module {
    single<UserService> {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://randomuser.me/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(EitherMoshiConverter(moshi))
            .build()
        retrofit.create(UserService::class.java)
    }
}