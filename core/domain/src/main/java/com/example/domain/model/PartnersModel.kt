package com.example.domain.model

data class PartnersModel(
    val id: Int,
    val category: PartnersCategoryModel,
    val title: String,
    val description: String,
    val city: String,
    val address: String,
    val site: String,
    val phone: String,
    val percentSale: Int?,
    val cashBack: Int?,
    val dateEnd: String?,
    val backgroundImage: String
)