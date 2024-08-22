package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class DonateToCharityUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(id: Int, amount: Int) = apiRepository.donateToCharity(id, amount)

}