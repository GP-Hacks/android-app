package com.example.domain.model

data class CharityModel(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val organization: String,
    val phone: String,
    val website: String,
    val goal: Int,
    val current: Int,
    val photo: String
)