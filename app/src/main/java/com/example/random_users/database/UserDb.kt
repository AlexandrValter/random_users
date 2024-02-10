package com.example.random_users.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDb(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title_name") val titleName: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "large_picture") val largePicture: String,
    @ColumnInfo(name = "medium_picture") val mediumPicture: String,
    @ColumnInfo(name = "thumbnail_picture") val thumbnailPicture: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "street_number") val streetNumber: Int,
    @ColumnInfo(name = "street_name") val streetName: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "postcode") val postcode: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "cell") val cell: String,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "timezone_description") val timezoneDescription: String,
    @ColumnInfo(name = "timezone_offset") val timezoneOffset: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "date_of_birthday") val dateOfBirthday: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "registration_date") val registrationDate: String,
    @ColumnInfo(name = "registration_age") val registrationAge: Int,
    @ColumnInfo(name = "nationality") val nationality: String?,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String
)