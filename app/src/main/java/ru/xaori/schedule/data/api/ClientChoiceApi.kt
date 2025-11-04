package ru.xaori.schedule.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.xaori.schedule.domain.model.clientChoice.ClientChoiceResponse

class ClientChoiceApi(private val client: HttpClient) {
    suspend fun getClients(): ClientChoiceResponse {
        return client.get("schedule/clients") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}