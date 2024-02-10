package com.example.random_users.model

import java.time.ZonedDateTime
import java.util.*

data class User(
    val id: Int? = 0,
    val titleName: String,
    val firstName: String,
    val lastName: String,
    val largePicture: String,
    val mediumPicture: String,
    val thumbnailPicture: String,
    val city: String,
    val streetNumber: Int,
    val streetName: String,
    val state: String,
    val country: String,
    val postcode: String,
    val phone: String,
    val cell: String,
    val gender: Gender?,
    val timezoneDescription: String,
    val timezoneOffset: String,
    val email: String,
    val dateOfBirthday: ZonedDateTime,
    val age: Int,
    val registrationDate: ZonedDateTime,
    val registrationAge: Int,
    val nationality: Nationality?,
    val latitude: String,
    val longitude: String
)

enum class Gender {
    MALE, FEMALE;

    companion object {
        fun fromString(value: String): Gender? {
            return when (value.lowercase(Locale.ROOT)) {
                "male" -> MALE
                "female" -> FEMALE
                else -> null
            }
        }
    }
}