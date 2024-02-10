package com.example.random_users.api

import androidx.annotation.Keep

@Keep
data class UserRes(
    val results: List<Person>
)

@Keep
data class Person(
    val name: Name,
    val picture: Picture,
    val location: Location,
    val phone: String,
    val cell: String,
    val gender: String,
    val email: String,
    val dob: DateWithAge,
    val registered: DateWithAge,
    val nat: String
)

@Keep
data class Name(
    val title: String,
    val first: String,
    val last: String
)

@Keep
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

@Keep
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

@Keep
data class Street(
    val number: Int,
    val name: String
)

@Keep
data class Coordinates(
    val latitude: String,
    val longitude: String
)

@Keep
data class Timezone(
    val offset: String,
    val description: String
)

@Keep
data class DateWithAge(
    val date: String,
    val age: Int
)