package com.example.domain.usecase

import com.example.domain.repository.LocalDataRepository
import javax.inject.Inject

class SetEmailAuthUseCase @Inject constructor(
    private val localDataRepository: LocalDataRepository
) {

    operator fun invoke(email: String) {
        localDataRepository.setEmailAuth(email)
    }

}