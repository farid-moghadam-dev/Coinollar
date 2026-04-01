package com.faridev.coinollar.domain.model

import java.util.UUID

data class CurrenciesData(
    val cryptoCurrency: List<Currency>,
    val currency: List<Currency>,
    val gold: List<Currency>
) {
    data class Currency(
        val uuid: UUID = UUID.randomUUID(),
        val changePercent: Double,
        val date: String,
        val name: String,
        val nameEn: String,
        val price: Double,
        val symbol: String,
        val time: String,
        val timeUnix: Int,
        val unit: String,
        val changeValue: Double? = null
    )
}
