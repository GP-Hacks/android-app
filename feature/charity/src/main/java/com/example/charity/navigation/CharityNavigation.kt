package com.example.charity.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.charity.CharityRoute
import kotlinx.serialization.Serializable

@Serializable
object Charity

fun NavController.navigateToCharity(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Charity, navOptions)

fun NavGraphBuilder.charityScreen(
) = composable<Charity> {
    CharityRoute()
}