package ru.xaori.schedule.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.xaori.schedule.data.store.SettingsStore
import ru.xaori.schedule.domain.repository.NotificationRepository
import ru.xaori.schedule.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val store: SettingsStore,
    private val notificationRepository: NotificationRepository
) : SettingsRepository {

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    override suspend fun setClient(client: String) {
        store.setClient(client)
        backgroundScope.launch {
            sendToken()
        }
    }

    override suspend fun setFcmToken(token: String) {
        store.setFcmToken(token)
        backgroundScope.launch {
            sendToken()
        }
    }

    private suspend fun sendToken() {
        val token = store.getFcmToken()
        val client = store.getClient()
        if (!token.isNullOrEmpty() && !client.isNullOrEmpty()) {
            try {
                notificationRepository.sendToken(client, token)
            } catch (e: Exception) {
                println("Ошибка при отправке токена: ${e.message}")
            }
        }
    }

    override suspend fun getClient(): String? = store.getClient()
    override suspend fun getFcmToken(): String? = store.getFcmToken()
}