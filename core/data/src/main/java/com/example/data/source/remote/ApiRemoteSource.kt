package com.example.data.source.remote

import android.util.Log
import com.example.common.model.ResultModel
import com.example.data.CardApiRoutes
import com.example.data.KdtApiRoutes
import com.example.data.response.CharityCategoriesResponse
import com.example.data.response.CharityListResponse
import com.example.data.response.CharityResponse
import com.example.data.response.ChatBotAnswerResponse
import com.example.data.response.FullInfoVoteResponse
import com.example.data.response.FullInfoVoteResponseGet
import com.example.data.response.ListNewsResponse
import com.example.data.response.NewsResponse
import com.example.data.response.PartnersCategoryResponse
import com.example.data.response.PartnersResponse
import com.example.data.response.PlaceListResponse
import com.example.data.response.PlaceResponse
import com.example.data.response.PlacesCategoriesResponse
import com.example.data.response.VoteListResponse
import com.example.data.response.VoteResponse
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

    suspend fun getVotes(): ResultModel<VoteListResponse> {
        return try {
            val request = httpClient.get(KdtApiRoutes.VOTES)

            if (request.status.value in 200..299) {
                val response: VoteListResponse = request.body()

                ResultModel.success(response)
            } else {
                Log.e("API RS VOTE", "code: ${request.status.value}; body: ${request.body<String>()}")
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS VOTE", e.toString())
            ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getFullInfoVoteById(id: Int): ResultModel<FullInfoVoteResponseGet> {
        try {
            val jsonBody = """
                {
                    "vote_id": $id
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.VOTES_GET) {
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                val response: FullInfoVoteResponseGet = request.body()

                ResultModel.success(response)
            } else {
                Log.e("API RS VOTE", "code: ${request.status.value}; body: ${request.body<String>()}")
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS VOTE", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun sendVoteRate(id: Int, vote: String, authData: String): ResultModel<Boolean> {
        try {
            val jsonBody = """
            {
                "vote_id": $id,
                "rating": $vote
            }
        """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.VOTES_RATE) {
                header(key = "Authorization", value = "Bearer $authData")
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

    suspend fun sendVoteChoice(id: Int, vote: String, authData: String): ResultModel<Boolean> {
        try {
            val jsonBody = """
            {
                "vote_id": $id,
                "choice": "$vote"
            }
        """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.VOTES_CHOICE) {
                header(key = "Authorization", value = "Bearer $authData")
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

    suspend fun sendVotePetition(id: Int, vote: String, authData: String): ResultModel<Boolean> {
        try {
            val jsonBody = """
            {
                "vote_id": $id,
                "support": "$vote"
            }
        """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.VOTES_PETITION) {
                header(key = "Authorization", value = "Bearer $authData")
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                ResultModel.success(true)
            } else {
                Log.e("API RS VOTE", "code: ${request.status.value}; body: ${request.body<String>()}")
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun donateToCharity(id: Int, amount: Int, authData: String): ResultModel<Boolean> {
        try {
            val jsonBody = """
                {
                    "collection_id": $id,
                    "amount": $amount
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.CHARITY_DONATE) {
                header(key = "Authorization", value = "Bearer $authData")
                setBody(jsonBody)
            }
            Log.i("API RS CHARITY", "Input: $jsonBody; Сode: ${request.status.value}; Data: ${request.body<String>()}")

            return if (request.status.value in 200..299) {
                ResultModel.success(true)
            } else {
                Log.e("API RS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getCharityCategories(): ResultModel<CharityCategoriesResponse> {
        return try {
            val request = httpClient.get(KdtApiRoutes.CHARITY_CATEGORIES)

            if (request.status.value in 200..299) {
                val response: CharityCategoriesResponse = request.body()

                ResultModel.success(response)
            } else {
                Log.e("API RS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS", e.toString())
            ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getCharity(category: String): ResultModel<CharityListResponse> {
        try {
            val jsonBody = """
                {
                    "category": "$category"
                }
            """.trimIndent()

            val request = httpClient.post(KdtApiRoutes.CHARITY_GET) {
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                val response: CharityListResponse = request.body()

                ResultModel.success(response)
            } else {
                Log.e("API RS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getPartnersCategories(): ResultModel<List<PartnersCategoryResponse>> {
        return try {
            val request = httpClient.get(CardApiRoutes.CATEGORIES)

            if (request.status.value in 200..299) {
                val response: List<PartnersCategoryResponse> = request.body()

                ResultModel.success(response)
            } else {
                Log.e("API RS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API RS", e.toString())
            ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getPartners(): ResultModel<List<PartnersResponse>> {
        try {
            val request = httpClient.get(CardApiRoutes.PARTNERS) {
                contentType(ContentType.Application.Json)
            }

            return if (request.status.value in 200..299) {
                val response: List<PartnersResponse> = request.body()

                ResultModel.success(response)
            } else {
                Log.i("API RS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.i("API RS", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun getPlacesCategories(): ResultModel<PlacesCategoriesResponse> {
        return try {
            val request = httpClient.get(KdtApiRoutes.PLACES_CATEGORIES)

            if (request.status.value in 200..299) {
                val response: PlacesCategoriesResponse = request.body()

                ResultModel.success(response)
            } else {
                Log.e("GET PLACES CAT", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("GET PLACES CAT", e.toString())
            ResultModel.failure("Непредвиденная ошибка.")
        }
    }

    suspend fun sendToken(token: String, authData: String): Boolean {
        try {
            val jsonBody = """
                {
                    "token": "$token"
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.USER_TOKEN) {
                header(key = "Authorization", value = "Bearer $authData")
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

    suspend fun buyTicketForPlace(id: Int, utc: String, authData: String): ResultModel<Boolean> {
        try {
            val jsonBody = """
                {
                    "place_id": $id,
                    "timestamp": "$utc"
                }
            """.trimIndent()
            val request = httpClient.post(KdtApiRoutes.PLACES_BUY) {
                header(key = "Authorization", value = "Bearer $authData")
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

    suspend fun getChatBotAnswerToUserRequest(userRequest: String, authData: String): ResultModel<ChatBotAnswerResponse> {
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
                header(key = "Authorization", value = "Bearer $authData")
                setBody(jsonBody)
            }

            return if (request.status.value in 200..299) {
                val result: ChatBotAnswerResponse = request.body()

                ResultModel.success(result)
            } else {
                Log.e("API ERRORS", request.body())
                ResultModel.failure("Непредвиденная ошибка.")
            }
        } catch (e: Exception) {
            Log.e("API ERRORS", e.toString())
            return ResultModel.failure("Непредвиденная ошибка.")
        }
    }

}

