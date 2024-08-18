package com.example.tatarstanresidentcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.chat_bot.navigation.ChatBot
import com.example.ui.theme.TatarstanResidentCardTheme
import com.example.ui.theme.mColors

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
//        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            splashScreen.setKeepOnScreenCondition { uiState is MainActivityUiState.Success }

            TatarstanResidentCardTheme {
                TatarstanResidentCardApp(
                    startDestination = ChatBot,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(mColors.surface)
                )
            }
        }
    }
}