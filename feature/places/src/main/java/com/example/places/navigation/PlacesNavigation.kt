package com.example.places.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.places.PlacesRoute
import kotlinx.serialization.Serializable

@Serializable
object Places

fun NavController.navigateToPlaces(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Places, navOptions)

fun NavGraphBuilder.placesScreen() = composable<Places>(
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
    PlacesRoute()
}