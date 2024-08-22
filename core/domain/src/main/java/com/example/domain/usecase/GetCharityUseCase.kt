package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetCharityUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(category: String = "all") = apiRepository.getListCharity(category)

}