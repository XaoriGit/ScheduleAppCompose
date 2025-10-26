package ru.xaori.schedule.data.repository

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.domain.model.ScheduleDataResponse
import ru.xaori.schedule.data.api.ScheduleApi
import ru.xaori.schedule.domain.repository.ClientChoiceRepository
import ru.xaori.schedule.domain.repository.ScheduleRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ScheduleRepositoryImpl(
    private val scheduleApi: ScheduleApi, private val clientChoiceRepository: ClientChoiceRepository
) : ScheduleRepository {
    @OptIn(ExperimentalTime::class)
    override suspend fun getSchedule(): Result<ScheduleDataResponse> {
        val clientName = clientChoiceRepository.getClient()

        return try {
            val response = scheduleApi.getSchedule(clientName, Clock.System.now())
            Result.success(response)
        } catch (_: IOException) {
            Result.failure(ApiError.Network)
        } catch (_: HttpRequestTimeoutException) {
            Result.failure(ApiError.Timeout)
        } catch (e: ClientRequestException) {
            Result.failure(ApiError.Server(e.response.status.value, e.message))
        } catch (e: ServerResponseException) {
            Result.failure(ApiError.Server(e.response.status.value, e.message))
        } catch (e: Throwable) {
            Result.failure(ApiError.Unknown(e))
        }
    }
}