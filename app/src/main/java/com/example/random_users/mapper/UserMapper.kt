package com.example.random_users.mapper

import com.example.random_users.api.Person
import com.example.random_users.database.UserDb
import com.example.random_users.model.Gender
import com.example.random_users.model.Nationality
import com.example.random_users.model.User
import java.time.ZonedDateTime

fun Person.toDomain(): User =
    User(
        titleName = name.title,
        firstName = name.first,
        lastName = name.last,
        largePicture = picture.large,
        mediumPicture = picture.medium,
        thumbnailPicture = picture.thumbnail,
        city = location.city,
        streetNumber = location.street.number,
        streetName = location.street.name,
        state = location.state,
        country = location.country,
        postcode = location.postcode,
        phone = phone,
        cell = cell,
        gender = Gender.fromString(gender),
        timezoneDescription = location.timezone.description,
        timezoneOffset = location.timezone.offset,
        email = email,
        dateOfBirthday = ZonedDateTime.parse(dob.date),
        age = dob.age,
        registrationDate = ZonedDateTime.parse(registered.date),
        registrationAge = registered.age,
        nationality = Nationality.fromString(nat),
        latitude = location.coordinates.latitude,
        longitude = location.coordinates.longitude
    )

fun UserDb.toDomain(): User =
    User(
        id = uid,
        titleName = titleName,
        firstName = firstName,
        lastName = lastName,
        largePicture = largePicture,
        mediumPicture = mediumPicture,
        thumbnailPicture = thumbnailPicture,
        city = city,
        streetNumber = streetNumber,
        streetName = streetName,
        state = state,
        country = country,
        postcode = postcode,
        phone = phone,
        cell = cell,
        gender = gender?.let { Gender.fromString(it) },
        timezoneDescription = timezoneDescription,
        timezoneOffset = timezoneOffset,
        email = email,
        dateOfBirthday = ZonedDateTime.parse(dateOfBirthday),
        age = age,
        registrationDate = ZonedDateTime.parse(registrationDate),
        registrationAge = registrationAge,
        nationality = nationality?.let { Nationality.fromString(it) },
        latitude = latitude,
        longitude = longitude
    )

fun User.toUserDb(): UserDb =
    UserDb(
        uid = id ?: 0,
        firstName = firstName,
        titleName = titleName,
        lastName = lastName,
        largePicture = largePicture,
        mediumPicture = mediumPicture,
        thumbnailPicture = thumbnailPicture,
        city = city,
        streetNumber = streetNumber,
        streetName = streetName,
        state = state,
        country = country,
        postcode = postcode,
        phone = phone,
        cell = cell,
        gender = gender?.name,
        timezoneDescription = timezoneDescription,
        timezoneOffset = timezoneOffset,
        email = email,
        dateOfBirthday = dateOfBirthday.toString(),
        age = age,
        registrationDate = registrationDate.toString(),
        registrationAge = registrationAge,
        nationality = nationality?.name,
        latitude = latitude,
        longitude = longitude
    )