package com.faridev.coinollar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.faridev.coinollar.feature.detail.DetailScreen
import com.faridev.coinollar.feature.home.HomeScreen
import com.faridev.coinollar.feature.splash.SplashScreen

@Composable
fun CoinollarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCurrencyClick = { symbol ->
                    navController.navigate(Screen.Detail.createRoute(symbol))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("symbol") { type = NavType.StringType })
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: return@composable
            DetailScreen(
                symbol = symbol,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
