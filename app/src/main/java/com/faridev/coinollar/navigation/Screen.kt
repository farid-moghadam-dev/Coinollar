package com.faridev.coinollar.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Detail : Screen("detail/{symbol}") {
        fun createRoute(symbol: String) = "detail/$symbol"
    }
}
