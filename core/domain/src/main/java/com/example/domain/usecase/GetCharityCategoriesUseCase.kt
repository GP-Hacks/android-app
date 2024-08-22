package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetCharityCategoriesUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke() = apiRepository.getCharityCategories()

}