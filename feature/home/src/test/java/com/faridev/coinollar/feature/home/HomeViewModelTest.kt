package com.faridev.coinollar.feature.home

import com.faridev.coinollar.core.common.result.Result
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.domain.usecase.FetchCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fetchCurrenciesUseCase: FetchCurrenciesUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fetchCurrenciesUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state triggers fetch and shows data on success`() = runTest {
        val testData = createTestCurrenciesData()
        coEvery { fetchCurrenciesUseCase() } returns Result.Success(testData)

        val viewModel = HomeViewModel(fetchCurrenciesUseCase)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.currenciesData)
        assertNull(state.errorMessage)
        assertEquals(1, state.currenciesData!!.cryptoCurrency.size)
    }

    @Test
    fun `initial state triggers fetch and shows error on failure`() = runTest {
        coEvery { fetchCurrenciesUseCase() } returns Result.Error("Network error")

        val viewModel = HomeViewModel(fetchCurrenciesUseCase)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.currenciesData)
        assertEquals("Network error", state.errorMessage)
    }

    @Test
    fun `refresh updates state correctly on success`() = runTest {
        val testData = createTestCurrenciesData()
        coEvery { fetchCurrenciesUseCase() } returns Result.Success(testData)

        val viewModel = HomeViewModel(fetchCurrenciesUseCase)
        advanceUntilIdle()

        viewModel.refresh()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isRefreshing)
        assertNotNull(state.currenciesData)
    }

    private fun createTestCurrenciesData() = CurrenciesData(
        cryptoCurrency = listOf(
            CurrenciesData.Currency(
                changePercent = 2.5, date = "2025-01-01", name = "بیت کوین",
                nameEn = "Bitcoin", price = 95000.0, symbol = "BTC",
                time = "12:00", timeUnix = 1704067200, unit = "دلار"
            )
        ),
        currency = emptyList(),
        gold = emptyList()
    )
}
