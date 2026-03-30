package com.faridev.coinollar.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faridev.coinollar.core.database.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies ORDER BY category, symbol")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currencies WHERE symbol = :symbol LIMIT 1")
    suspend fun getCurrencyBySymbol(symbol: String): CurrencyEntity?

    @Query("SELECT * FROM currencies WHERE category = :category")
    suspend fun getCurrenciesByCategory(category: String): List<CurrencyEntity>

    @Query("DELETE FROM currencies")
    suspend fun deleteAll()
}
