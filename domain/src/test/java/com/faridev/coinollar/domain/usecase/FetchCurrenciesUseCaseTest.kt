package com.faridev.coinollar.domain.usecase

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FetchCurrenciesUseCaseTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: FetchCurrenciesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = FetchCurrenciesUseCase(repository)
    }

    @Test
    fun `invoke returns success when repository succeeds`() = runTest {
        val expected = CurrenciesData(
            cryptoCurrency = listOf(createTestCurrency("BTC", "Bitcoin")),
            currency = listOf(createTestCurrency("USD", "US Dollar")),
            gold = listOf(createTestCurrency("XAUUSD", "Gold Ounce"))
        )
        coEvery { repository.getCurrenciesList() } returns Result.Success(expected)

        val result = useCase()

        assertTrue(result is Result.Success)
        assertEquals(expected, (result as Result.Success).data)
        coVerify(exactly = 1) { repository.getCurrenciesList() }
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getCurrenciesList() } returns Result.Error(errorMessage)

        val result = useCase()

        assertTrue(result is Result.Error)
        assertEquals(errorMessage, (result as Result.Error).message)
    }

    private fun createTestCurrency(symbol: String, nameEn: String) = CurrenciesData.Currency(
        changePercent = 1.5,
        date = "2025-01-01",
        name = "تست",
        nameEn = nameEn,
        price = 100.0,
        symbol = symbol,
        time = "12:00",
        timeUnix = 1704067200,
        unit = "دلار"
    )
}
