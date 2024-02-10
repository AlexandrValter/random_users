package com.example.random_users.common

import com.example.random_users.main.AmountUsers

fun Int.toAmountUsers(): AmountUsers? {
    return when (this) {
        10 -> AmountUsers.TEN
        25 -> AmountUsers.TWENTY_FIVE
        50 -> AmountUsers.FIFTY
        else -> null
    }
}

fun AmountUsers.toInt(): Int {
    return when (this) {
        AmountUsers.TEN -> 10
        AmountUsers.TWENTY_FIVE -> 25
        AmountUsers.FIFTY -> 50
    }
}

fun errorParser(json: String): Map<String, String> {
    val map = mutableMapOf<String, String>()
    val errorIndex = json.indexOf("error")
    if (errorIndex != -1) {
        val startIndex = json.indexOf("\"", errorIndex)
        if (startIndex != -1) {
            val endIndex = json.indexOf("\"", startIndex + 1)
            if (endIndex != -1) {
                val value = json.substring(startIndex + 1, endIndex)
                map["error"] = value
            }
        }
    }
    return map
}