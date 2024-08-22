package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetPlacesCategoriesUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke() = apiRepository.getPlacesCategories()

}