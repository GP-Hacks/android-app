package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class SendVoteUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(id: Int, category: String, vote: String) = apiRepository.sendVote(id, category, vote)

}