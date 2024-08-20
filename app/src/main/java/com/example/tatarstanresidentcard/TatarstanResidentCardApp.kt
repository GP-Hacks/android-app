package com.example.tatarstanresidentcard

 import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chat_bot.navigation.ChatBot
import com.example.home.navigation.Home
import com.example.tatarstanresidentcard.navigation.TatarstanResidentCardNavHost

@Composable
fun TatarstanResidentCardApp(
    modifier: Modifier = Modifier,
    startDestination: Any = Home
) {
    TatarstanResidentCardNavHost(
        startDestination = startDestination,
        modifier = modifier
    )
}