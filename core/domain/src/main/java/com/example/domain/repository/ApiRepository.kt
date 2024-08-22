package com.example.domain.repository

import androidx.paging.PagingData
import com.example.common.model.ResultModel
import com.example.domain.model.CharityModel
import com.example.domain.model.ChatBotAnswerModel
import com.example.domain.model.NewsModel
import com.example.domain.model.PartnersCategoryModel
import com.example.domain.model.PartnersModel
import com.example.domain.model.PlaceModel
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    fun getCharityCategories(): Flow<ResultModel<List<String>>>

    fun getListCharity(category: String): Flow<ResultModel<List<CharityModel>>>

    fun getPartnersCategories(): Flow<ResultModel<List<PartnersCategoryModel>>>

    suspend fun sendDeviceToken(token: String)

    suspend fun sendDeviceToken()

    fun getPartnersList(): Flow<ResultModel<List<PartnersModel>>>

    fun getPlacesCategories(): Flow<ResultModel<List<String>>>

    fun getPlaces(latitude: Double, longitude: Double, category: String): Flow<ResultModel<List<PlaceModel>>>

    fun getNews(pageSize: Int): Flow<PagingData<NewsModel>>

    fun getChatBotAnswerToUserRequest(userRequest: String): Flow<ResultModel<ChatBotAnswerModel>>

    fun buyTicketForPlace(id: Int, date: Long, time: Long): Flow<ResultModel<Boolean>>

}