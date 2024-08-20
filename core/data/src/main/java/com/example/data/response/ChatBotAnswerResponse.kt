package com.example.data.response

import com.example.domain.model.ChatBotAnswerModel
import kotlinx.serialization.Serializable

@Serializable
data class ChatBotAnswerResponse(
    val response: String
)

fun ChatBotAnswerResponse.toChatBotAnswerModel() = ChatBotAnswerModel(
    response = response
)