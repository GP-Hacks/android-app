package com.example.data.response

import com.example.domain.model.CharityModel
import kotlinx.serialization.Serializable

@Serializable
data class CharityResponse(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val organization: String,
    val phone: String,
    val website: String,
    val goal: Int,
    val current: Int
)

fun CharityResponse.toCharityModel() = CharityModel(id, category, name, description, organization, phone, website, goal, current)

@Serializable
data class CharityListResponse(
    val response: List<CharityResponse>
)