package com.example.chat_bot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.ResultModel
import com.example.domain.model.ChatBotAnswerModel
import com.example.domain.usecase.CheckAuthUseCase
import com.example.domain.usecase.GetChatBotAnswerByUserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val getChatBotAnswerByUserRequest: GetChatBotAnswerByUserRequest,
    private val checkAuthUseCase: CheckAuthUseCase
): ViewModel() {

    private val _chatHistory: MutableStateFlow<List<Pair<String, ResultModel<ChatBotAnswerModel>>>> = MutableStateFlow(
        emptyList()
    )
    val chatHistory: StateFlow<List<Pair<String, ResultModel<ChatBotAnswerModel>>>>
        get() = _chatHistory

    private var currentRequest = 0

    fun checkAuth() = checkAuthUseCase()

    fun sendUserRequest(userRequest: String) {
        if (userRequest != "") {
            viewModelScope.launch {
                getChatBotAnswerByUserRequest(userRequest)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        if (currentRequest == _chatHistory.value.size) {
                            _chatHistory.value += Pair(userRequest, it)
                        } else {
                            val b = _chatHistory.value.toMutableList()
                            b[currentRequest] = Pair(userRequest, it)
                            _chatHistory.value = b
                            currentRequest++
                        }
                    }
            }
        }
    }

}