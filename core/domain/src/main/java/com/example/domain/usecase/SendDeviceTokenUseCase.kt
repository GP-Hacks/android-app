package com.example.domain.usecase

import android.util.Log
import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class SendDeviceTokenUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    suspend operator fun invoke(token: String) {
        apiRepository.sendDeviceToken(token)
    }

    suspend operator fun invoke() {
        apiRepository.sendDeviceToken()
    }

}