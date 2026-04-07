package com.faridev.coinollar.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.faridev.coinollar.core.designsystem.component.ShimmerCurrencyItem
import com.faridev.coinollar.core.designsystem.theme.HeaderEndStepGradient
import com.faridev.coinollar.domain.model.CurrenciesData
import com.faridev.coinollar.feature.home.component.CurrencyItemCard
import com.faridev.coinollar.feature.home.component.HeaderSection

private val HEADER_CURRENCY_SYMBOLS = listOf(
    "USD", "USDT_IRT", "IR_GOLD_18K", "IR_COIN_EMAMI",
    "IR_COIN_HALF", "IR_COIN_QUARTER", "IR_COIN_BAHAR", "XAUUSD"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCurrencyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HeaderEndStepGradient),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading && uiState.currenciesData == null -> {
                ShimmerLoadingContent()
            }
            uiState.errorMessage != null && uiState.currenciesData == null -> {
                ErrorContent(
                    message = uiState.errorMessage!!,
                    onRetry = viewModel::fetchCurrencies
                )
            }
            uiState.currenciesData != null -> {
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = viewModel::refresh,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CurrencyListContent(
                        currenciesData = uiState.currenciesData!!,
                        onCurrencyClick = onCurrencyClick
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyListContent(
    currenciesData: CurrenciesData,
    onCurrencyClick: (String) -> Unit
) {
    val allCurrencies = remember(currenciesData) {
        currenciesData.gold + currenciesData.currency + currenciesData.cryptoCurrency
    }
    val headerCurrencies = remember(allCurrencies) {
        val orderMap = HEADER_CURRENCY_SYMBOLS
            .withIndex()
            .associate { it.value to it.index }
        allCurrencies
            .filter { it.symbol in orderMap }
            .sortedBy { orderMap[it.symbol] }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            item { HeaderSection(headerCurrencies) }
            items(
                key = { it.uuid },
                items = allCurrencies
            ) { currency ->
                CurrencyItemCard(
                    currency = currency,
                    onClick = { onCurrencyClick(currency.symbol) }
                )
            }
        }
    }
}

@Composable
private fun ShimmerLoadingContent() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 60.dp)
        ) {
            items(8) {
                ShimmerCurrencyItem()
            }
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
