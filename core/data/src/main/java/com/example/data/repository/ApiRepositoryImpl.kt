package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.model.ResultModel
import com.example.data.response.toChatBotAnswerModel
import com.example.data.response.toPlaceModel
import com.example.data.source.paging.NewsPagingSource
import com.example.data.source.remote.ApiRemoteSource
import com.example.data.utils.millisToUtcString
import com.example.domain.model.ChatBotAnswerModel
import com.example.domain.model.NewsModel
import com.example.domain.model.PlaceModel
import com.example.domain.repository.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiRemoteSource: ApiRemoteSource
): ApiRepository {
    override fun sendDeviceToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            apiRemoteSource.sendToken(token)
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