package com.example.votes.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.votes.VotesRoute
import kotlinx.serialization.Serializable

@Serializable
object Votes

fun NavController.navigateToVotes(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Votes, navOptions)

fun NavGraphBuilder.votesScreen(
) = composable<Votes>(
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
    VotesRoute()
}