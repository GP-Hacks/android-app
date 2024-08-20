package com.example.chat_bot.navigation

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
//    changeVisibleNavBar: () -> Unit
) = composable<ChatBot> {
//    changeVisibleNavBar()
    ChatBotRoute()
}