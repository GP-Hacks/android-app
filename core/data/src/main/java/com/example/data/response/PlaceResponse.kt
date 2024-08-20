package com.example.data.response

import com.example.domain.model.PlaceModel
import kotlinx.serialization.Serializable

@Serializable
data class PlaceListResponse(
    val response: List<PlaceResponse>
)

@Serializable
data class PlaceResponse(
    val id: Int,
    val category: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val name: String,
    val tel: String,
    val website: String,
    val photos: List<PlacesPhotoResponse>
)

@Serializable
data class PlacesPhotoResponse(
    val url: String
)

fun PlaceResponse.toPlaceModel() = PlaceModel(
    id, category, description, latitude, longitude, location, name, tel, website, photos.map { it.url }
)