package com.faridev.coinollar.core.common.util

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentJalaliDate(): String {
    val formatter = PersianDateFormat("l j F Y")
    return formatter.format(PersianDate())
}

fun getCurrentGeorgianDate(): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    return formatter.format(Date())
}
