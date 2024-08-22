package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.model.ResultModel
import com.example.data.response.toChatBotAnswerModel
import com.example.data.response.toPartnersCategoryModel
import com.example.data.response.toPartnersModel
import com.example.data.response.toPlaceModel
import com.example.data.source.local.SharedPreferenceLocalSource
import com.example.data.source.paging.NewsPagingSource
import com.example.data.source.remote.ApiRemoteSource
import com.example.data.utils.millisToUtcString
import com.example.domain.model.ChatBotAnswerModel
import com.example.domain.model.NewsModel
import com.example.domain.model.PartnersCategoryModel
import com.example.domain.model.PartnersModel
import com.example.domain.model.PlaceModel
import com.example.domain.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiRemoteSource: ApiRemoteSource,
    private val sharedPreferenceLocalSource: SharedPreferenceLocalSource
): ApiRepository {
    override fun getPartnersCategories(): Flow<ResultModel<List<PartnersCategoryModel>>> = flow {
        emit(ResultModel.loading())

        val result = apiRemoteSource.getPartnersCategories()

        if (result.status == ResultModel.Status.SUCCESS) {
            emit(ResultModel.success(result.data!!.map { it.toPartnersCategoryModel() }))
        } else {
            emit(ResultModel.failure(result.message))
        }
    }

    override suspend fun sendDeviceToken(token: String) {
        sharedPreferenceLocalSource.setDeviceToken(token)
//        TODO(Проверка на авторизацию)
        if (true) {
            val result = apiRemoteSource.sendToken(token)

            sharedPreferenceLocalSource.setBindDeviceTokenStatus(result)
        } else {
            sharedPreferenceLocalSource.setBindDeviceTokenStatus(false)
        }
    }

    override suspend fun sendDeviceToken() {
//        TODO(Проверка на авторизацию)
        if (true) {
            val status = sharedPreferenceLocalSource.getBindDeviceTokenStatus()

            if (!status) {
                val token = sharedPreferenceLocalSource.getDeviceToken()

                if (token != null) {
                    val result = apiRemoteSource.sendToken(token)

                    sharedPreferenceLocalSource.setBindDeviceTokenStatus(result)
                }
            }
        }
    }

    override fun getPartnersList(): Flow<ResultModel<List<PartnersModel>>> = flow {
        emit(ResultModel.loading())

        val result = apiRemoteSource.getPartners()

        if (result.status == ResultModel.Status.SUCCESS) {
            emit(ResultModel.success(result.data!!.map { it.toPartnersModel() }))
        } else {
            emit(ResultModel.failure(result.message))
        }
    }

    override fun getPlacesCategories(): Flow<ResultModel<List<String>>> = flow {
        emit(ResultModel.loading())

        val result = apiRemoteSource.getPlacesCategories()

        if (result.status == ResultModel.Status.SUCCESS) {
            emit(ResultModel.success(result.data!!.response))
        } else {
            emit(ResultModel.failure(result.message))
        }
    }

    override fun getPlaces(
        latitude: Double,
        longitude: Double,
        category: String
    ): Flow<ResultModel<List<PlaceModel>>> = flow {
        emit(ResultModel.loading())

        val result = apiRemoteSource.getListPlaces(latitude, longitude, category)

        if (result.status == ResultModel.Status.SUCCESS) {
            emit(ResultModel.success(result.data!!.response.map { it.toPlaceModel() }))
        } else {
            emit(ResultModel.failure(result.message))
        }
    }

    override fun getNews(pageSize: Int): Flow<PagingData<NewsModel>> = Pager(
        config = PagingConfig(pageSize = pageSize, prefetchDistance = 2),
        pagingSourceFactory = {
            NewsPagingSource(
                apiRemoteSource = apiRemoteSource,
                pageSize = pageSize
            )
        }
    ).flow

    override fun getChatBotAnswerToUserRequest(userRequest: String): Flow<ResultModel<ChatBotAnswerModel>> = flow {
        emit(ResultModel.loading())

        val result = apiRemoteSource.getChatBotAnswerToUserRequest(userRequest)

        if (result.status == ResultModel.Status.SUCCESS) {
            emit(ResultModel.success(result.data!!.toChatBotAnswerModel()))
        } else {
            emit(ResultModel.failure(result.message))
        }
    }

    override fun buyTicketForPlace(id: Int, date: Long, time: Long): Flow<ResultModel<Boolean>> = flow {
        emit(ResultModel.loading())

        val result = apiRemoteSource.buyTicketForPlace(id, millisToUtcString(date + time))

        if (result.status == ResultModel.Status.SUCCESS) {
            emit(ResultModel.success(result.data!!))
        } else {
            emit(ResultModel.failure(result.message))
        }
    }
}