package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetFullInfoVoteByIdUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(id: Int) = apiRepository.getFullInfoVoteById(id)

}