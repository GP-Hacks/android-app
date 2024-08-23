package com.example.domain.model

data class FullVoteModel(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val organization: String,
    val end: String,
    val photo: String,
    val options: List<String>?,
    val stats: Map<String, Int>
)