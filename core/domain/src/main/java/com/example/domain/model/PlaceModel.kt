package com.example.domain.model

data class PlaceModel(
    val id: Int,
    val category: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val name: String,
    val tel: String,
    val website: String,
    val photos: List<String>,
    val cost: Int,
    val times: List<String>
)
