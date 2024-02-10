package com.example.random_users.api

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import okhttp3.ResponseBody
import okio.ByteString.Companion.decodeHex
import retrofit2.Converter
import retrofit2.Response

class MoshiResponseBodyToEitherConverter<T>(private val adapter: JsonAdapter<T>) :
    Converter<ResponseBody, Either<Exception, T>> {

    companion object {
        private val UTF8_BOM = "EFBBBF".decodeHex()
    }

    override fun convert(value: ResponseBody): Either<Exception, T> {
        val source = value.source()
        value.use {
            try {
                if (source.rangeEquals(0, UTF8_BOM)) {
                    source.skip(UTF8_BOM.size.toLong())
                }
                val reader = JsonReader.of(source)
                reader.isLenient = true

                val result: T = adapter.fromJson(reader)
                    ?: return ResponseFromServerNull.left()
                if (reader.peek() != JsonReader.Token.END_DOCUMENT)
                    return JsonDataException("JSON document was not fully consumed.").left()
                return Either.Right(result)
            } catch (ex: Exception) {
                return ex.left()
            }
        }
    }
}

class MoshiResponseBodyToEitherUnitConverter : Converter<ResponseBody, Either<Exception, Unit>> {

    override fun convert(value: ResponseBody): Either<Exception, Unit> {
        value.use {
            return Unit.right()
        }
    }
}

object ResponseFromServerNull : NullPointerException()

fun <T> Response<Either<Exception, T>>.liftEither(): Either<Throwable, T> {
    return when {
        isSuccessful -> body() ?: ResponseNotFound.left()
        else -> ErrorBody(errorBody()?.string()).left()
    }
}