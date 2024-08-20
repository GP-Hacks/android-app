package com.example.places.navigation

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

fun NavGraphBuilder.placesScreen() = composable<Places> {
    PlacesRoute()
}