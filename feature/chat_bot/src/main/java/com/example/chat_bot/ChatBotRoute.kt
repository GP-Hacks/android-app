package com.example.chat_bot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chat_bot.components.BotMessageLoading
import com.example.chat_bot.components.ChatBotTopBar
import com.example.chat_bot.components.Message
import com.example.chat_bot.components.MessageTextField
import com.example.common.model.ResultModel
import com.example.ui.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotRoute(
    viewModel: ChatBotViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    var userRequest by remember {
        mutableStateOf("")
    }
    val chatHistory by viewModel.chatHistory.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.surface),
        topBar = {
            ChatBotTopBar(
                title = "Дракон (ИИ Ассистент)",
                image = "",
                onBackPressed = { onBack() }
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
                    text = userRequest,
                    onSendButtonClick = {
                        viewModel.sendUserRequest(userRequest)
                        userRequest = ""
                    },
                    onChangeMessage = { userRequest = it },
                    placeholder = "Спросите",
                    enabled = viewModel.checkAuth()
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
                        when (it.second.status) {
                            ResultModel.Status.FAILURE -> {
                                Message(text = it.second.message.toString(), isUserMessage = false, isError = true)
                            }
                            ResultModel.Status.LOADING -> {
                                BotMessageLoading()
                            }
                            else -> {
                                Message(text = it.second.data?.response.toString(), isUserMessage = false, isError = false)
                            }
                        }
                    }
                    item {
                        Message(text = it.first, isUserMessage = true, isError = false)
                    }
                }

                if (!viewModel.checkAuth()) {
                    item {
                        Text(
                            text = "Войдите в аккаунт для использования",
                            color = Color(0xFF8F8F8F),
                            fontSize = 13.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                                .background(Color(0xFFF4F4F4), RoundedCornerShape(30.dp))
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                item {
                    Text(
                        text = "Я - ваш персональный ИИ помощник",
                        color = Color(0xFF8F8F8F),
                        fontSize = 13.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .background(Color(0xFFF4F4F4), RoundedCornerShape(30.dp))
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}