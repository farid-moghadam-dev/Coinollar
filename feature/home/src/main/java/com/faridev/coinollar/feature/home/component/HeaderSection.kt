package com.faridev.coinollar.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.faridev.coinollar.core.common.util.getCurrentGeorgianDate
import com.faridev.coinollar.core.common.util.getCurrentJalaliDate
import com.faridev.coinollar.core.common.util.getFormattedPrice
import com.faridev.coinollar.core.designsystem.icon.getSymbolIcon
import com.faridev.coinollar.core.designsystem.theme.HeaderEndStepGradient
import com.faridev.coinollar.core.designsystem.theme.HeaderItemBackgroundColor
import com.faridev.coinollar.core.designsystem.theme.HeaderStartStepGradient
import com.faridev.coinollar.domain.model.CurrenciesData

@Composable
fun HeaderSection(
    headerCurrencies: List<CurrenciesData.Currency>,
    modifier: Modifier = Modifier
) {
    val jalaliDate = remember { getCurrentJalaliDate() }
    val georgianDate = remember { getCurrentGeorgianDate() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(HeaderStartStepGradient, HeaderEndStepGradient)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                "امروز،",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
            )
            Text(
                jalaliDate,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
            )
            Text(
                georgianDate,
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    textDirection = TextDirection.Ltr
                )
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
        ) {
            items(
                key = { it.uuid },
                items = headerCurrencies
            ) { item ->
                HeaderCurrencyCard(item)
            }
        }
    }
}

@Composable
private fun HeaderCurrencyCard(
    currency: CurrenciesData.Currency,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .padding(4.dp)
            .width(120.dp)
    ) {
        val (symbolIcon, timeText, contentCard) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(contentCard) {
                    bottom.linkTo(parent.bottom)
                },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = HeaderItemBackgroundColor,
                contentColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 6.dp, start = 6.dp, end = 6.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = currency.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = getFormattedPrice(currency.price, currency.unit),
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp)
                )
            }
        }

        Image(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(45.dp)
                .constrainAs(symbolIcon) {
                    top.linkTo(contentCard.top)
                    bottom.linkTo(contentCard.top)
                    end.linkTo(parent.end)
                },
            painter = painterResource(getSymbolIcon(currency.symbol)),
            contentScale = ContentScale.Fit,
            contentDescription = currency.nameEn
        )

        Card(
            modifier = Modifier
                .constrainAs(timeText) {
                    top.linkTo(contentCard.top)
                    bottom.linkTo(contentCard.top)
                    start.linkTo(parent.start)
                },
            colors = CardDefaults.cardColors(
                containerColor = HeaderItemBackgroundColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = currency.time,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
