package com.example.portal_care.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.portal_care.PortalCareRoute
import kotlinx.serialization.Serializable

@Serializable
object PortalCare

fun NavController.navigateToPortalCare(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(PortalCare, navOptions)

fun NavGraphBuilder.portalCareScreen(
    onBack: () -> Unit
) = composable<PortalCare>(
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
    }
) {
    PortalCareRoute {
        onBack()
    }
}