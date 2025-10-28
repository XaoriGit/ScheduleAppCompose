package ru.xaori.schedule.data.repository

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.domain.model.ScheduleDataResponse
import ru.xaori.schedule.data.api.ScheduleApi
import ru.xaori.schedule.domain.model.ScheduleResult
import ru.xaori.schedule.domain.repository.ClientChoiceRepository
import ru.xaori.schedule.domain.repository.ScheduleCacheRepository
import ru.xaori.schedule.domain.repository.ScheduleRepository
import java.io.IOException
import java.nio.channels.UnresolvedAddressException
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ScheduleRepositoryImpl(
    private val scheduleApi: ScheduleApi,
    private val clientChoiceRepository: ClientChoiceRepository,
    private val cacheRepository: ScheduleCacheRepository
) : ScheduleRepository {
    @OptIn(ExperimentalTime::class)
    override suspend fun getSchedule(): Result<ScheduleResult> {
        val clientName = clientChoiceRepository.getClient()
        return try {
            val response = scheduleApi.getSchedule(clientName, Clock.System.now())
            cacheRepository.saveSchedule(response)
            Result.success(ScheduleResult(response, false))
        } catch (e: Exception) {
            val cached = cacheRepository.getCachedSchedule()
            when (e) {
                is UnresolvedAddressException,
                is IOException -> {
                    if (cached != null)
                        Result.success(ScheduleResult(cached, true))
                    else
                        Result.failure(ApiError.Network)
                }

                is ClientRequestException -> Result.failure(ApiError.Server(e.response.status.value, e.message))
                is ServerResponseException -> Result.failure(ApiError.Server(e.response.status.value, e.message))
                else -> Result.failure(ApiError.Unknown(e))
            }
        }
    }
}