package com.faridev.coinollar.core.database.di

import androidx.room.Room
import com.faridev.coinollar.core.database.CoinollarDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            CoinollarDatabase::class.java,
            "coinollar_database"
        ).build()
    }

    single { get<CoinollarDatabase>().currencyDao() }
}
