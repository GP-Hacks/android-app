package com.example.domain.usecase

import android.util.Log
import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class SendDeviceTokenUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(token: String) {
        Log.i("DEVICE TOKEN", token)
    }

}