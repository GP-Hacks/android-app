package com.example.charity.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
) = composable<Charity>(
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
    CharityRoute()
}