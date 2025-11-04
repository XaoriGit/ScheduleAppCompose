package ru.xaori.schedule.domain.repository

import ru.xaori.schedule.domain.model.clientChoice.ClientChoiceResponse

interface ClientChoiceRepository {
    suspend fun getClients(): Result<ClientChoiceResponse>
    suspend fun getClient(): String
    suspend fun setClient(value: String)
    suspend fun isClient(): Boolean
}