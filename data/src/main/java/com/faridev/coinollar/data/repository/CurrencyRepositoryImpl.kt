package com.faridev.coinollar.data.repository

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.core.database.dao.CurrencyDao
import com.faridev.coinollar.core.network.api.CoinollarApi
import com.faridev.coinollar.data.mapper.toCurrenciesData
import com.faridev.coinollar.data.mapper.toDomain
import com.faridev.coinollar.data.mapper.toEntities
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.repository.CurrencyRepository
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

class CurrencyRepositoryImpl(
    private val api: CoinollarApi,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    override suspend fun getCurrenciesList(): Result<CurrenciesData> {
        return try {
            val response = api.getCurrenciesData()
            val entities = response.toEntities()
            currencyDao.insertAll(entities)
            Result.Success(response.toDomain())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e, "Network request failed, trying cache")
            loadFromCache() ?: Result.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getCurrencyBySymbol(symbol: String): Result<CurrenciesData.Currency> {
        return try {
            val entity = currencyDao.getCurrencyBySymbol(symbol)
            if (entity != null) {
                Result.Success(entity.toDomain())
            } else {
                Result.Error("Currency not found")
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    private suspend fun loadFromCache(): Result<CurrenciesData>? {
        val cached = currencyDao.getAllCurrencies()
        return if (cached.isNotEmpty()) {
            Result.Success(cached.toCurrenciesData())
        } else {
            null
        }
    }
}
