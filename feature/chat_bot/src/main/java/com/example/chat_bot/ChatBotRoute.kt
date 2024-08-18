package com.example.chat_bot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chat_bot.components.ChatBotTopBar
import com.example.chat_bot.components.Message
import com.example.chat_bot.components.MessageTextField
import com.example.ui.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotRoute(
    viewModel: ChatBotViewModel = hiltViewModel()
) {
    val chatHistory by viewModel.chatHistory.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.surface),
        topBar = {
            ChatBotTopBar(
                title = "Дракон (ИИ Ассистент)",
                additionalInfo = "Онлайн",
                image = "",
                onBackPressed = { /*TODO*/ }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                MessageTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.userRequest,
                    onSendButtonClick = { viewModel.sendUserRequest() },
                    onChangeMessage = { viewModel.changeUserRequest(it) },
                    placeholder = "Спросите"
                )
            }
        }
    ) { paddingValues ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.surface)
                    .padding(top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp),
                reverseLayout = true
            ) {
                item {
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() + 16.dp))
                }

                chatHistory.reversed().forEach {
                    item {
                        Message(text = it.second, isUserMessage = false)
                    }
                    item {
                        Message(text = it.first, isUserMessage = true)
                    }
                }
            }
        }
    }
}