package com.example.random_users.api

import arrow.core.Either
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("api/1.4")
    suspend fun getUsers(@Query("results") amount: Int): Response<Either<Exception, UserRes>>
}