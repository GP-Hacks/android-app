package com.example.data.response

import com.example.domain.model.VoteModel
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val organization: String,
    val end: String,
    val photo: String,
    val options: List<String>?
)

@Serializable
data class VoteListResponse(
    val response: List<VoteResponse>
)

fun VoteResponse.toVoteModel() = VoteModel(id, category, name, description, organization, end, photo, options)