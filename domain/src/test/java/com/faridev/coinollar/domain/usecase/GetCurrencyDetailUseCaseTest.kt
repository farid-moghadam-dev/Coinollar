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

class GetCurrencyDetailUseCaseTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: GetCurrencyDetailUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCurrencyDetailUseCase(repository)
    }

    @Test
    fun `invoke returns currency when found`() = runTest {
        val expected = CurrenciesData.Currency(
            changePercent = 2.5,
            date = "2025-01-01",
            name = "بیت کوین",
            nameEn = "Bitcoin",
            price = 95000.0,
            symbol = "BTC",
            time = "12:00",
            timeUnix = 1704067200,
            unit = "دلار"
        )
        coEvery { repository.getCurrencyBySymbol("BTC") } returns Result.Success(expected)

        val result = useCase("BTC")

        assertTrue(result is Result.Success)
        assertEquals(expected, (result as Result.Success).data)
        coVerify(exactly = 1) { repository.getCurrencyBySymbol("BTC") }
    }

    @Test
    fun `invoke returns error when currency not found`() = runTest {
        coEvery { repository.getCurrencyBySymbol("UNKNOWN") } returns Result.Error("Currency not found")

        val result = useCase("UNKNOWN")

        assertTrue(result is Result.Error)
        assertEquals("Currency not found", (result as Result.Error).message)
    }
}
