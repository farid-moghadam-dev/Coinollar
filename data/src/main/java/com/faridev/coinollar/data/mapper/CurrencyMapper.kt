package com.faridev.coinollar.data.mapper

import com.faridev.coinollar.core.database.entity.CurrencyEntity
import com.faridev.coinollar.core.network.model.CryptoCurrencyDto
import com.faridev.coinollar.core.network.model.CurrenciesResponseDto
import com.faridev.coinollar.core.network.model.GoldAndCurrencyDto
import com.faridev.coinollar.domain.model.CurrenciesData

// DTO -> Domain

fun CurrenciesResponseDto.toDomain(): CurrenciesData {
    return CurrenciesData(
        cryptoCurrency = cryptoCurrency.map { it.toDomain() },
        currency = currency.map { it.toDomain() },
        gold = gold.map { it.toDomain() }
    )
}

fun GoldAndCurrencyDto.toDomain(): CurrenciesData.Currency {
    return CurrenciesData.Currency(
        changePercent = changePercent,
        date = date,
        name = name,
        nameEn = nameEn,
        price = price.toDouble(),
        symbol = symbol,
        time = time,
        timeUnix = timeUnix,
        unit = unit,
        changeValue = changeValue.toDouble()
    )
}

fun CryptoCurrencyDto.toDomain(): CurrenciesData.Currency {
    return CurrenciesData.Currency(
        changePercent = changePercent,
        date = date,
        name = name,
        nameEn = nameEn,
        price = price.toDoubleOrNull() ?: 0.0,
        symbol = symbol,
        time = time,
        timeUnix = timeUnix,
        unit = unit
    )
}

// DTO -> Entity

fun CurrenciesResponseDto.toEntities(): List<CurrencyEntity> {
    val cryptoEntities = cryptoCurrency.map { it.toEntity("crypto") }
    val currencyEntities = currency.map { it.toEntity("currency") }
    val goldEntities = gold.map { it.toEntity("gold") }
    return cryptoEntities + currencyEntities + goldEntities
}

fun CryptoCurrencyDto.toEntity(category: String): CurrencyEntity {
    return CurrencyEntity(
        symbol = symbol,
        name = name,
        nameEn = nameEn,
        price = price.toDoubleOrNull() ?: 0.0,
        changePercent = changePercent,
        changeValue = null,
        date = date,
        time = time,
        timeUnix = timeUnix,
        unit = unit,
        category = category
    )
}

fun GoldAndCurrencyDto.toEntity(category: String): CurrencyEntity {
    return CurrencyEntity(
        symbol = symbol,
        name = name,
        nameEn = nameEn,
        price = price.toDouble(),
        changePercent = changePercent,
        changeValue = changeValue.toDouble(),
        date = date,
        time = time,
        timeUnix = timeUnix,
        unit = unit,
        category = category
    )
}

// Entity -> Domain

fun CurrencyEntity.toDomain(): CurrenciesData.Currency {
    return CurrenciesData.Currency(
        changePercent = changePercent,
        date = date,
        name = name,
        nameEn = nameEn,
        price = price,
        symbol = symbol,
        time = time,
        timeUnix = timeUnix,
        unit = unit,
        changeValue = changeValue
    )
}

fun List<CurrencyEntity>.toCurrenciesData(): CurrenciesData {
    return CurrenciesData(
        cryptoCurrency = filter { it.category == "crypto" }.map { it.toDomain() },
        currency = filter { it.category == "currency" }.map { it.toDomain() },
        gold = filter { it.category == "gold" }.map { it.toDomain() }
    )
}
