package com.example.data.response

import com.example.domain.model.PlaceTicketModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceTicketResponseGet(
    val response: List<PlaceTicketResponse>
)

@Serializable
data class PlaceTicketResponse(
    val id: Int,
    val name: String,
    val location: String,
    @SerialName("event_time") val eventTime: String
)

fun PlaceTicketResponse.toPlaceTicketModel() = PlaceTicketModel(id, name, location, eventTime)