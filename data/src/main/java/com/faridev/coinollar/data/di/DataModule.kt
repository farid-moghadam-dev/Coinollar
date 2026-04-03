package com.faridev.coinollar.data.di

import com.faridev.coinollar.data.repository.CurrencyRepositoryImpl
import com.faridev.coinollar.domain.repository.CurrencyRepository
import com.faridev.coinollar.domain.usecase.FetchCurrenciesUseCase
import com.faridev.coinollar.domain.usecase.GetCurrencyDetailUseCase
import org.koin.dsl.module

val dataModule = module {

    single<CurrencyRepository> { CurrencyRepositoryImpl(get(), get()) }

    factory { FetchCurrenciesUseCase(get()) }

    factory { GetCurrencyDetailUseCase(get()) }
}
