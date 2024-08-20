package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(pageSize: Int) = apiRepository.getNews(pageSize)

}