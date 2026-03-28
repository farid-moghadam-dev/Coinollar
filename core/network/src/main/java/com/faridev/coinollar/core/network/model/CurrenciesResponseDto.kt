package com.faridev.coinollar.core.network.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CurrenciesResponseDto(
    @SerialName("cryptocurrency")
    val cryptoCurrency: List<CryptoCurrencyDto>,
    @SerialName("currency")
    val currency: List<GoldAndCurrencyDto>,
    @SerialName("gold")
    val gold: List<GoldAndCurrencyDto>
)

@Keep
@Serializable
data class CryptoCurrencyDto(
    @SerialName("change_percent")
    val changePercent: Double,
    @SerialName("date")
    val date: String,
    @SerialName("name")
    val name: String,
    @SerialName("name_en")
    val nameEn: String,
    @SerialName("price")
    val price: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("time")
    val time: String,
    @SerialName("time_unix")
    val timeUnix: Int,
    @SerialName("unit")
    val unit: String
)

@Keep
@Serializable
data class GoldAndCurrencyDto(
    @SerialName("change_percent")
    val changePercent: Double,
    @SerialName("change_value")
    val changeValue: Int,
    @SerialName("date")
    val date: String,
    @SerialName("name")
    val name: String,
    @SerialName("name_en")
    val nameEn: String,
    @SerialName("price")
    val price: Int,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("time")
    val time: String,
    @SerialName("time_unix")
    val timeUnix: Int,
    @SerialName("unit")
    val unit: String
)
