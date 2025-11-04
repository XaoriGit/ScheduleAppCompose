package ru.xaori.schedule.domain.model.clientChoice

import kotlinx.serialization.Serializable

@Serializable
data class ClientChoiceResponse(
    val groups: List<String>,
    val teachers: List<String>
)