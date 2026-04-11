package com.faridev.coinollar.di

import com.faridev.coinollar.feature.detail.DetailViewModel
import com.faridev.coinollar.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { (symbol: String) -> DetailViewModel(symbol, get()) }
}
