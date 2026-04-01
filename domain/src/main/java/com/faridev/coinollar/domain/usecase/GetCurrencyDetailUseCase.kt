package com.faridev.coinollar.domain.usecase

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.repository.CurrencyRepository

class GetCurrencyDetailUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(symbol: String): Result<CurrenciesData.Currency> =
        repository.getCurrencyBySymbol(symbol)
}
