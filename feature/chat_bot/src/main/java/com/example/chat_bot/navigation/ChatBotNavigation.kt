package com.example.chat_bot.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.chat_bot.ChatBotRoute
import kotlinx.serialization.Serializable

@Serializable
object ChatBot

fun NavController.navigateToChatBot(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(ChatBot, navOptions)

fun NavGraphBuilder.chatBotScreen(
    navigateToCharity: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToNews: () -> Unit,
    navigateToPayments: () -> Unit,
    navigateToPlaces: () -> Unit,
    navigateToPortalCare: () -> Unit,
    navigateToServices: () -> Unit,
    navigateToStocks: () -> Unit,
    navigateToVotes: () -> Unit,
    onBack: () -> Unit
) = composable<ChatBot>(
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
    ChatBotRoute(
        onBack = onBack,
        navigateToCharity = navigateToCharity,
        navigateToHome = navigateToHome,
        navigateToNews = navigateToNews,
        navigateToPayments = navigateToPayments,
        navigateToPlaces = navigateToPlaces,
        navigateToPortalCare = navigateToPortalCare,
        navigateToServices = navigateToServices,
        navigateToStocks = navigateToStocks,
        navigateToVotes = navigateToVotes)
}