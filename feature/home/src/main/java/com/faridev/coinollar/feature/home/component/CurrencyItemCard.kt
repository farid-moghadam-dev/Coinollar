package com.faridev.coinollar.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faridev.coinollar.core.common.util.getFormattedPrice
import com.faridev.coinollar.core.designsystem.icon.getChangePercentColor
import com.faridev.coinollar.core.designsystem.icon.getSymbolIcon
import com.faridev.coinollar.core.designsystem.theme.MainItemBackgroundColor
import com.faridev.coinollar.domain.model.CurrenciesData

@Composable
fun CurrencyItemCard(
    currency: CurrenciesData.Currency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 3.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MainItemBackgroundColor,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.weight(0.6f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Image(
                    modifier = Modifier.size(55.dp),
                    painter = painterResource(getSymbolIcon(currency.symbol)),
                    contentScale = ContentScale.Fit,
                    contentDescription = currency.nameEn
                )

                Spacer(modifier = Modifier.width(5.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        currency.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            currency.time,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            "%${currency.changePercent}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 15.sp,
                                color = getChangePercentColor(currency.changePercent),
                                textDirection = TextDirection.Ltr
                            )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    getFormattedPrice(currency.price, currency.unit),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp)
                )
                currency.changeValue?.let {
                    Text(
                        getFormattedPrice(it, currency.unit, showUnit = false, showSign = true),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 15.sp,
                            color = getChangePercentColor(currency.changePercent),
                            textDirection = TextDirection.Ltr
                        )
                    )
                }
            }
        }
    }
}
