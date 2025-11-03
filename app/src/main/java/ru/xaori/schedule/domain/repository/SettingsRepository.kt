package ru.xaori.schedule.domain.repository

interface SettingsRepository {
    suspend fun setClient(client: String)
    suspend fun setFcmToken(token: String)

    suspend fun getClient(): String?
    suspend fun getFcmToken(): String?
}