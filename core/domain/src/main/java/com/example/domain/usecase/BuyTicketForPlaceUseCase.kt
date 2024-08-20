package com.example.domain.usecase

import com.example.domain.repository.ApiRepository
import javax.inject.Inject

class BuyTicketForPlaceUseCase @Inject constructor(
    private val apiRepository: ApiRepository
){

    operator fun invoke(id: Int, date: Long, time: Long) = apiRepository.buyTicketForPlace(id, date, time)

}