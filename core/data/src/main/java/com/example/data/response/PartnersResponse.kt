package com.example.data.response

import com.example.domain.model.PartnersCategoryModel
import com.example.domain.model.PartnersModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartnersResponse(
    val id: Int,
    val category: PartnersCategoryResponse,
    val title: String,
    val description: String,
    val city: String,
    val address: String,
    val site: String,
    val phone: String,
    @SerialName("percent_sale") val percentSale: Int?,
    val cashback: Int?,
    @SerialName("date_end") val dateEnd: String?,
    @SerialName("background_image") val backgroundImage: String
)

fun PartnersResponse.toPartnersModel() = PartnersModel(id, category.toPartnersCategoryModel(), title, description, city, address, site, phone, percentSale, cashback, dateEnd, backgroundImage)