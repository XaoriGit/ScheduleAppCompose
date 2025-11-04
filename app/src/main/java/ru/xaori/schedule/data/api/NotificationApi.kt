package ru.xaori.schedule.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI
import ru.xaori.schedule.domain.model.notification.TokenRequest
import kotlin.time.ExperimentalTime

class NotificationApi(private val client: HttpClient) {
    @OptIn(ExperimentalTime::class, InternalAPI::class)
    suspend fun setToken(clientName: String, token: String) {
        val requestBody = TokenRequest(fcmToken = token, clientName = clientName)
        return client.post("notification/save_fcm_token/") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()
    }
}