package com.faridev.coinollar.data.repository

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.core.database.dao.CurrencyDao
import com.faridev.coinollar.core.database.entity.CurrencyEntity
import com.faridev.coinollar.core.network.api.CoinollarApi
import com.faridev.coinollar.core.network.model.CryptoCurrencyDto
import com.faridev.coinollar.core.network.model.CurrenciesResponseDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CurrencyRepositoryImplTest {

    private lateinit var api: CoinollarApi
    private lateinit var dao: CurrencyDao
    private lateinit var repository: CurrencyRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        dao = mockk(relaxed = true)
        repository = CurrencyRepositoryImpl(api, dao)
    }

    @Test
    fun `getCurrenciesList returns success and caches data`() = runTest {
        val response = createTestResponse()
        coEvery { api.getCurrenciesData() } returns response

        val result = repository.getCurrenciesList()

        assertTrue(result is Result.Success)
        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `getCurrenciesList falls back to cache on network error`() = runTest {
        val cachedEntities = listOf(createTestEntity("BTC", "crypto"))
        coEvery { api.getCurrenciesData() } throws IOException("No network")
        coEvery { dao.getAllCurrencies() } returns cachedEntities

        val result = repository.getCurrenciesList()

        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(1, data.cryptoCurrency.size)
    }

    @Test
    fun `getCurrenciesList returns error when no cache available`() = runTest {
        coEvery { api.getCurrenciesData() } throws IOException("No network")
        coEvery { dao.getAllCurrencies() } returns emptyList()

        val result = repository.getCurrenciesList()

        assertTrue(result is Result.Error)
    }

    @Test
    fun `getCurrencyBySymbol returns success when found`() = runTest {
        val entity = createTestEntity("BTC", "crypto")
        coEvery { dao.getCurrencyBySymbol("BTC") } returns entity

        val result = repository.getCurrencyBySymbol("BTC")

        assertTrue(result is Result.Success)
        assertEquals("BTC", (result as Result.Success).data.symbol)
    }

    @Test
    fun `getCurrencyBySymbol returns error when not found`() = runTest {
        coEvery { dao.getCurrencyBySymbol("UNKNOWN") } returns null

        val result = repository.getCurrencyBySymbol("UNKNOWN")

        assertTrue(result is Result.Error)
    }

    private fun createTestResponse() = CurrenciesResponseDto(
        cryptoCurrency = listOf(
            CryptoCurrencyDto(
                changePercent = 1.5, date = "2025-01-01", name = "بیت کوین",
                nameEn = "Bitcoin", price = "95000", symbol = "BTC",
                time = "12:00", timeUnix = 1704067200, unit = "دلار"
            )
        ),
        currency = emptyList(),
        gold = emptyList()
    )

    private fun createTestEntity(symbol: String, category: String) = CurrencyEntity(
        symbol = symbol, name = "تست", nameEn = "Test",
        price = 100.0, changePercent = 1.0, changeValue = null,
        date = "2025-01-01", time = "12:00", timeUnix = 1704067200,
        unit = "دلار", category = category
    )
}
