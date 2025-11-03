package ru.xaori.schedule.domain.repository

interface NotificationRepository {
    suspend fun sendToken(clientName: String, token: String)
}