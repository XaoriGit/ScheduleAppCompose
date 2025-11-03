package ru.xaori.schedule.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    @SerialName("fcm_token") val fcmToken: String,
    @SerialName("client_name") val clientName: String
)