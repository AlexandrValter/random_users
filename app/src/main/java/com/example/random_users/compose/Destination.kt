package com.example.random_users.compose

typealias Destination = String

const val main: Destination = "main"
const val user: Destination = "user?id={id}"

val userBuild: (Int) -> Destination = { "user?id=$it" }