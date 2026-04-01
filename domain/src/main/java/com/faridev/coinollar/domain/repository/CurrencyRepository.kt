package com.faridev.coinollar.domain.repository

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData

interface CurrencyRepository {
    suspend fun getCurrenciesList(): Result<CurrenciesData>
    suspend fun getCurrencyBySymbol(symbol: String): Result<CurrenciesData.Currency>
}
