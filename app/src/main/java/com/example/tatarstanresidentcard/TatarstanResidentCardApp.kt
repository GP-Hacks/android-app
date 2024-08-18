package com.example.tatarstanresidentcard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chat_bot.navigation.ChatBot
import com.example.tatarstanresidentcard.navigation.TatarstanResidentCardNavHost

@Composable
fun TatarstanResidentCardApp(
    modifier: Modifier = Modifier,
    startDestination: Any = ChatBot
) {
    TatarstanResidentCardNavHost(
        startDestination = startDestination,
        modifier = modifier
    )
}