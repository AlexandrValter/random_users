package com.example.random_users.model

import com.example.random_users.R
import java.util.*

enum class Nationality(val flagResId: Int) {
    AU(R.drawable.ic_flag_au),
    BR(R.drawable.ic_flag_br),
    CA(R.drawable.ic_flag_ca),
    CH(R.drawable.ic_flag_ch),
    DE(R.drawable.ic_flag_de),
    DK(R.drawable.ic_flag_dk),
    ES(R.drawable.ic_flag_es),
    FI(R.drawable.ic_flag_fi),
    FR(R.drawable.ic_flag_fr),
    GB(R.drawable.ic_flag_en),
    IE(R.drawable.ic_flag_ie),
    IN(R.drawable.ic_flag_in),
    IR(R.drawable.ic_flag_ir),
    MX(R.drawable.ic_flag_mx),
    NL(R.drawable.ic_flag_nl),
    NO(R.drawable.ic_flag_no),
    NZ(R.drawable.ic_flag_nz),
    RS(R.drawable.ic_flag_rs),
    TR(R.drawable.ic_flag_tr),
    UA(R.drawable.ic_flag_ua),
    US(R.drawable.ic_flag_us);

    companion object {
        fun fromString(value: String): Nationality? {
            return when (value.lowercase(Locale.ROOT)) {
                "au" -> AU
                "br" -> BR
                "ca" -> CA
                "ch" -> CH
                "de" -> DE
                "dk" -> DK
                "es" -> ES
                "fi" -> FI
                "fr" -> FR
                "gb" -> GB
                "ie" -> IE
                "in" -> IN
                "ir" -> IR
                "mx" -> MX
                "nl" -> NL
                "no" -> NO
                "nz" -> NZ
                "rs" -> RS
                "tr" -> TR
                "ua" -> UA
                "us" -> US
                else -> null
            }
        }
    }
}