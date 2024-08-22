package com.example.stocks.navigation

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
//    changeVisibleNavBar: () -> Unit
) = composable<Stocks> {
//    changeVisibleNavBar()
    StocksRoute()
}