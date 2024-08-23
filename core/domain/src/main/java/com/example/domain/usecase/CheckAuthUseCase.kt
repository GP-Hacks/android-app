package com.example.domain.usecase

import com.example.domain.repository.LocalDataRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val localDataRepository: LocalDataRepository
) {

    operator fun invoke() = localDataRepository.checkAuth()

}