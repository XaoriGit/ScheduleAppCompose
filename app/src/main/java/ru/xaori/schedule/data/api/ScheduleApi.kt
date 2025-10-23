package ru.xaori.schedule.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.xaori.schedule.domain.model.ScheduleDataResponse
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ScheduleApi(private val client: HttpClient) {
    @OptIn(ExperimentalTime::class)
    suspend fun getSchedule(clientName: String, clientTime: Instant): ScheduleDataResponse {
        return client.get("schedule") {
            contentType(ContentType.Application.Json)
            headers {
                append("X-CLIENT-TIME", clientTime.toString())
            }
            url {
                parameters.append("client_name", clientName)
            }
        }.body()
    }
}