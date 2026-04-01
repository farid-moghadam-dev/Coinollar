package com.faridev.coinollar.domain.usecase

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.repository.CurrencyRepository

class FetchCurrenciesUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): Result<CurrenciesData> = repository.getCurrenciesList()
}
