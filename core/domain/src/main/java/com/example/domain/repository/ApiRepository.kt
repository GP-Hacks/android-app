package com.example.domain.repository

import androidx.paging.PagingData
import com.example.common.model.ResultModel
import com.example.domain.model.ChatBotAnswerModel
import com.example.domain.model.NewsModel
import com.example.domain.model.PlaceModel
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    fun getPlaces(latitude: Double, longitude: Double, category: String): Flow<ResultModel<List<PlaceModel>>>

    fun getNews(pageSize: Int): Flow<PagingData<NewsModel>>

    fun getChatBotAnswerToUserRequest(userRequest: String): Flow<ResultModel<ChatBotAnswerModel>>

    fun buyTicketForPlace(id: Int, date: Long, time: Long): Flow<ResultModel<Boolean>>

}