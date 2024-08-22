package com.example.data.response

import com.example.domain.model.PartnersCategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class PartnersCategoryResponse(
    val id: Int,
    val title: String,
    val color: String,
    val icon: String
)

fun PartnersCategoryResponse.toPartnersCategoryModel() = PartnersCategoryModel(id, title, color, icon)