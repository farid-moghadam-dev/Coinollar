package com.faridev.coinollar.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faridev.coinollar.core.common.util.getFormattedPrice
import com.faridev.coinollar.core.designsystem.icon.getChangePercentColor
import com.faridev.coinollar.core.designsystem.icon.getSymbolIcon
import com.faridev.coinollar.core.designsystem.theme.HeaderEndStepGradient
import com.faridev.coinollar.core.designsystem.theme.HeaderItemBackgroundColor
import com.faridev.coinollar.core.designsystem.theme.HeaderStartStepGradient
import com.faridev.coinollar.core.designsystem.theme.MainItemBackgroundColor
import com.faridev.coinollar.domain.model.CurrenciesData
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(
    symbol: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = koinViewModel(parameters = { parametersOf(symbol) })
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HeaderStartStepGradient, HeaderEndStepGradient)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> CircularProgressIndicator(color = Color.White)
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage!!,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            uiState.currency != null -> {
                DetailContent(
                    currency = uiState.currency!!,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    currency: CurrenciesData.Currency,
    onBackClick: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        tint = Color.White,
                        contentDescription = "Back"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Currency icon
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(getSymbolIcon(currency.symbol)),
                contentScale = ContentScale.Fit,
                contentDescription = currency.nameEn
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Currency name
            Text(
                text = currency.name,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp)
            )
            Text(
                text = currency.nameEn,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    textDirection = TextDirection.Ltr
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Price
            Text(
                text = getFormattedPrice(currency.price, currency.unit),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp)
            )

            // Change percent
            Text(
                text = "%${currency.changePercent}",
                color = getChangePercentColor(currency.changePercent),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    textDirection = TextDirection.Ltr
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Detail card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MainItemBackgroundColor,
                    contentColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailRow(label = "نماد", value = currency.symbol)
                    HorizontalDivider(color = HeaderItemBackgroundColor)
                    DetailRow(label = "واحد", value = currency.unit)
                    HorizontalDivider(color = HeaderItemBackgroundColor)
                    DetailRow(label = "تاریخ", value = currency.date)
                    HorizontalDivider(color = HeaderItemBackgroundColor)
                    DetailRow(label = "ساعت", value = currency.time)
                    currency.changeValue?.let { changeVal ->
                        HorizontalDivider(color = HeaderItemBackgroundColor)
                        DetailRow(
                            label = "تغییر قیمت",
                            value = getFormattedPrice(
                                changeVal,
                                currency.unit,
                                showUnit = false,
                                showSign = true
                            ),
                            valueColor = getChangePercentColor(currency.changePercent)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Color.White
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
        )
        Text(
            text = value,
            color = valueColor,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
        )
    }
}
