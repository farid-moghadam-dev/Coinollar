package com.faridev.coinollar.core.designsystem.icon

import androidx.compose.ui.graphics.Color
import com.faridev.coinollar.core.designsystem.R
import com.faridev.coinollar.core.designsystem.theme.Green
import com.faridev.coinollar.core.designsystem.theme.Red

fun getChangePercentColor(changePercent: Double): Color {
    return when {
        changePercent < 0 -> Red
        changePercent > 0 -> Green
        else -> Color.White
    }
}

fun getSymbolIcon(symbol: String): Int {
    return when (symbol) {
        "IR_GOLD_18K", "IR_GOLD_24K", "IR_GOLD_MELTED", "XAUUSD" -> R.drawable.ic_gold_bullion
        "IR_COIN_1G", "IR_COIN_QUARTER", "IR_COIN_HALF", "IR_COIN_EMAMI", "IR_COIN_BAHAR" -> R.drawable.ic_coin
        "BTC" -> R.drawable.ic_btc
        "ETH" -> R.drawable.ic_eth
        "USDT", "USDT_IRT" -> R.drawable.ic_usdt
        "XRP" -> R.drawable.ic_xrp
        "BNB" -> R.drawable.ic_bnb
        "SOL" -> R.drawable.ic_sol
        "USDC" -> R.drawable.ic_usdc
        "TRX" -> R.drawable.ic_trx
        "DOGE" -> R.drawable.ic_doge
        "ADA" -> R.drawable.ic_ada
        "LINK" -> R.drawable.ic_link
        "XLM" -> R.drawable.ic_xlm
        "AVAX" -> R.drawable.ic_avax
        "SHIB" -> R.drawable.ic_shib
        "LTC" -> R.drawable.ic_ltc
        "DOT" -> R.drawable.ic_dot
        "UNI" -> R.drawable.ic_uni
        "ATOM" -> R.drawable.ic_atom
        "FIL" -> R.drawable.ic_fil
        "USD" -> R.drawable.us
        "EUR" -> R.drawable.eu
        "AED" -> R.drawable.ae
        "GBP" -> R.drawable.gb
        "JPY" -> R.drawable.jp
        "KWD" -> R.drawable.kw
        "AUD" -> R.drawable.au
        "CAD" -> R.drawable.ca
        "CNY" -> R.drawable.cn
        "TRY" -> R.drawable.tr
        "SAR" -> R.drawable.sa
        "CHF" -> R.drawable.ch
        "INR" -> R.drawable.ind
        "PKR" -> R.drawable.pk
        "IQD" -> R.drawable.iq
        "SYP" -> R.drawable.sy
        "SEK" -> R.drawable.se
        "QAR" -> R.drawable.qa
        "OMR" -> R.drawable.om
        "BHD" -> R.drawable.bh
        "AFN" -> R.drawable.af
        "MYR" -> R.drawable.my
        "THB" -> R.drawable.th
        "RUB" -> R.drawable.ru
        "AZN" -> R.drawable.az
        "AMD" -> R.drawable.am
        "GEL" -> R.drawable.ge
        else -> R.drawable.ic_unknown_flag
    }
}
