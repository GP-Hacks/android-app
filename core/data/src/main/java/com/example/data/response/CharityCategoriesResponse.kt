package com.example.data.response

import kotlinx.serialization.Serializable

@Serializable
data class CharityCategoriesResponse(
    val categories: List<String>
)
