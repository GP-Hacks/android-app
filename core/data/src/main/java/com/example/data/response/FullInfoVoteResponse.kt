package com.example.data.response

import com.example.domain.model.FullInfoVoteModel
import kotlinx.serialization.Serializable

@Serializable
data class FullInfoVoteResponse(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val organization: String,
    val end: String,
    val photo: String,
    val options: List<String>?,
    val stats: Map<String, Int> = mapOf(),
    val choice: String? = null,
    val support: String? = null,
    val rate: Float? = null,
    val mid: Float? = null
)

@Serializable
data class FullInfoVoteResponseGet(
    val response: FullInfoVoteResponse
)

fun FullInfoVoteResponse.toFullInfoVoteModel() = FullInfoVoteModel(id, category, name, description, organization, end, photo, options, stats, choice, support, rate?.toInt(), mid)