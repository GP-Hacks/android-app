package com.example.auth.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.auth.AuthRoute
import kotlinx.serialization.Serializable

@Serializable
object Auth

fun NavController.navigateToAuth(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Auth, navOptions)

fun NavGraphBuilder.authScreen(
    onSuccessAuth: () -> Unit
) = composable<Auth>(
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
    AuthRoute(onSuccessAuth)
}