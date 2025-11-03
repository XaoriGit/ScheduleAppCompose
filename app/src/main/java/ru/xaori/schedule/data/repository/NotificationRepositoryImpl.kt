package ru.xaori.schedule.data.repository

import ru.xaori.schedule.data.api.NotificationApi
import ru.xaori.schedule.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val notificationApi: NotificationApi
): NotificationRepository {
    override suspend fun sendToken(clientName: String, token: String) {
        notificationApi.setToken(clientName, token)
    }
}