package com.example.data.response

import kotlinx.serialization.Serializable

@Serializable
data class VotesCategoriesListResponse(
    val response: List<String>
)