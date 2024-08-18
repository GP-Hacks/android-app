package com.example.chat_bot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(

): ViewModel() {

    var userRequest by mutableStateOf("")
        private set

    fun changeUserRequest(newUserRequest: String) {
        userRequest = newUserRequest
    }

    private val _chatHistory: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        emptyList()
    )
    val chatHistory: StateFlow<List<Pair<String, String>>>
        get() = _chatHistory

    fun sendUserRequest() {
        viewModelScope.launch {
//            delay(5000)
            val b = _chatHistory.value.toMutableList()
            b.add(Pair(userRequest, "bot answer"))
            _chatHistory.value = b
            userRequest = ""
        }
    }

}