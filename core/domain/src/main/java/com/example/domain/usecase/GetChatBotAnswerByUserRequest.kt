package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetChatBotAnswerByUserRequest @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(userRequest: String) = apiRepository.getChatBotAnswerToUserRequest(userRequest)

}