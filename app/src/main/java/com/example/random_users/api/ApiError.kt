package com.example.random_users.api

import androidx.annotation.Keep

sealed class ApiError : Throwable()

object ResponseNotFound : ApiError()

@Keep
open class ErrorBody(val raw: String?) : ApiError()