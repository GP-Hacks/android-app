package com.example.data.source.remote

import android.util.Log
import com.example.common.model.ResultModel
import com.example.data.CardApiRoutes
import com.example.data.KdtApiRoutes
import com.example.data.response.ChatBotAnswerResponse
import com.example.data.response.ListNewsResponse
import com.example.data.response.NewsResponse
import com.example.data.response.PlaceListResponse
import com.example.data.response.PlaceResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiRemoteSource {

    private val httpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun sendToken(token: String): Boolean {
        try {
            val jsonBody = """
                {
                    "token": "$token"
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.USER_TOKEN) {
                header(key = "Authorization", value = "Bearer example@gmail.com")
                setBody(jsonBody)
            }

            return if (request.status.value == 200) {
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("SEND TOKEN", e.toString())
            return false
        }
    }

    suspend fun buyTicketForPlace(id: Int, utc: String): ResultModel<Boolean> {
        try {
            val jsonBody = """
                {
                    "place_id": $id,
                    "timestamp": "$utc"
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.PLACES_BUY) {
                header(key = "Authorization", value = "Bearer example@gmail.com")
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                ResultModel.success(true)
            } else {
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getListPlaces(latitude: Double, longitude: Double, category: String): ResultModel<PlaceListResponse> {
        try {
            val jsonBody = """
                {
                    "latitude": $latitude,
                    "longitude": $longitude,
                    "category": "$category"
                }
            """.trimIndent()

            val request = httpClient.post(KdtApiRoutes.PLACES_GET) {
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                val res: PlaceListResponse = request.body()

                ResultModel.success(res)
            } else {
                Log.e("API RS", request.body())
                Log.e("API RS", request.status.value.toString())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS", e.toString())
            return ResultModel.failure(message = "Непредвиденная ошибка.")
        }
    }

    suspend fun getListNews(page: Int, pageSize: Int): ResultModel<ListNewsResponse> {
        try {
            val request = httpClient.get(CardApiRoutes.NEWS) {
                contentType(ContentType.Application.Json)
                parameter("page", page)
                parameter("pageSize", pageSize)
            }

            return if (request.status.value in 200..299) {
                val res: ListNewsResponse = request.body()

                return ResultModel.success(res)
            } else {
                Log.e("API RS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getChatBotAnswerToUserRequest(userRequest: String): ResultModel<ChatBotAnswerResponse> {
        try {
            val jsonBody = """
                {
                    "messages": [
                        {
                            "role": "user",
                            "content": "$userRequest"
                        }
                    ]
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.CHAT_ASK) {
                contentType(ContentType.Application.Json)
                header(key = "Authorization", value = "Bearer example@gmail.com")
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                val result: ChatBotAnswerResponse = request.body()

                ResultModel.success(result)
            } else {
                // TODO("Добавить обработчик ошибок")
                Log.e("API ERRORS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API ERRORS", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

}

