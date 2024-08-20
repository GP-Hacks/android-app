package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) {

    operator fun invoke(latitude: Double = 55.79718, longitude: Double = 49.106453, category: String = "all") = apiRepository.getPlaces(latitude, longitude, category)

}