package ru.xaori.schedule.domain.repository

interface NotificationRepository {
    suspend fun subscribeToTopic(topic: String): Result<Unit>
}