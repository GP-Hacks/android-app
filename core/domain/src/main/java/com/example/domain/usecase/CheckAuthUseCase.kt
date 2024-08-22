package com.example.domain.usecase

import com.example.domain.repository.LocalDataRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val apiRepository: LocalDataRepository
) {

    operator fun invoke() = apiRepository.checkAuth()

}