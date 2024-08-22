package com.example.stocks.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.stocks.StocksRoute
import kotlinx.serialization.Serializable

@Serializable
object Stocks

fun NavController.navigateToStocks(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Stocks, navOptions)

fun NavGraphBuilder.stocksScreen(
) = composable<Stocks>(
    enterTransition = {
        slideInHorizontally(
            initialOffsetX = { it / 6 }
        ) + fadeIn()
    },
    exitTransition = {
        fadeOut()
    },
    popEnterTransition = {
        fadeIn()
    },
    popExitTransition = {
        slideOutHorizontally(
            targetOffsetX = { it / 6 }
        ) + fadeOut()
    },
) {
    StocksRoute()
}