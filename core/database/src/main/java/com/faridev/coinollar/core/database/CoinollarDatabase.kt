package com.faridev.coinollar.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faridev.coinollar.core.database.dao.CurrencyDao
import com.faridev.coinollar.core.database.entity.CurrencyEntity

@Database(
    entities = [CurrencyEntity::class],
    version = 1,
    exportSchema = true
)
abstract class CoinollarDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
