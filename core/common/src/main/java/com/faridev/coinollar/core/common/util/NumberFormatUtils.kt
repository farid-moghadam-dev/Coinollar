package com.faridev.coinollar.core.common.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

fun getFormattedPrice(
    price: Double,
    unit: String,
    showUnit: Boolean = true,
    showSign: Boolean = false
): String {
    val formatter = NumberFormat.getInstance(Locale.US)
    val formattedNumber = formatter.format(price)
    val unitFormatted = when {
        !showUnit -> formattedNumber
        unit == "دلار" -> "$$formattedNumber"
        unit == "تومان" -> "${formattedNumber}ت"
        else -> formattedNumber
    }
    if (!showSign) return unitFormatted

    val sign = when {
        price > 0 -> "+"
        else -> ""
    }

    return sign + unitFormatted
}

fun formatCompactNumber(value: Long, decimals: Int = 2): String {
    val absValue = abs(value).toDouble()

    val (divisor, suffix) = when {
        absValue >= 1_000_000_000_000 -> 1_000_000_000_000.0 to "T"
        absValue >= 1_000_000_000 -> 1_000_000_000.0 to "B"
        absValue >= 1_000_000 -> 1_000_000.0 to "M"
        absValue >= 1_000 -> 1_000.0 to "K"
        else -> 1.0 to ""
    }

    val scaled = BigDecimal(absValue / divisor)
        .setScale(decimals, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString()

    val sign = if (value < 0) "-" else ""

    return "$sign$scaled$suffix".trim()
}
