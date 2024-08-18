package com.example.tatarstanresidentcard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.chat_bot.navigation.ChatBot
import com.example.chat_bot.navigation.chatBotScreen

@Composable
fun TatarstanResidentCardNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = ChatBot,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        chatBotScreen()

    }

}