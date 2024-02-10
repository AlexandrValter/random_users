package com.example.random_users.api

import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

class EitherMoshiConverter(
    private val moshi: Moshi,
    private val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(moshi)
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val argType = (type as ParameterizedType).actualTypeArguments.last()
        return if (argType == Unit::javaClass.get() || argType == Void.TYPE) MoshiResponseBodyToEitherUnitConverter()
        else MoshiResponseBodyToEitherConverter(
            moshi.adapter<Any>(
                argType,
                jsonAnnotations(annotations)!!
            )
        )
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return converterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return converterFactory.stringConverter(type, annotations, retrofit)
    }

    private fun jsonAnnotations(annotations: Array<Annotation>): Set<Annotation?>? {
        var result: MutableSet<Annotation?>? = null
        for (annotation in annotations) {
            annotation as java.lang.annotation.Annotation
            if (annotation.annotationType().isAnnotationPresent(JsonQualifier::class.java)) {
                if (result == null) result = LinkedHashSet()
                result.add(annotation)
            }
        }
        return if (result != null) Collections.unmodifiableSet(result) else emptySet<Annotation>()
    }
}