package com.example.data.response

import com.example.domain.model.NewsModel
import kotlinx.serialization.Serializable

@Serializable
data class ListNewsResponse(
    val result: ResultNewsResponse
)

@Serializable
data class ResultNewsResponse(
    val posts: List<NewsResponse>
)

@Serializable
data class NewsResponse(
    val id: Int,
    val title: String,
    val content: String,
    val pictureUrl: String
)

fun NewsResponse.toNewsModel() = NewsModel(
    id, title, content, pictureUrl
)