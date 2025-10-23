package ru.xaori.schedule.data.repository

import ru.xaori.schedule.domain.model.ScheduleDataResponse
import ru.xaori.schedule.data.repository.ClientChoiceRepository
import ru.xaori.schedule.data.api.ScheduleApi
import ru.xaori.schedule.presentation.state.ResultWrapper
import ru.xaori.schedule.presentation.state.mapError
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ScheduleRepository(
    private val scheduleApi: ScheduleApi,
    private val clientChoiceRepository: ClientChoiceRepository
) {
    @OptIn(ExperimentalTime::class)
    suspend fun getSchedule(): ResultWrapper<ScheduleDataResponse> {
        return try {
            val clientName = clientChoiceRepository.getClient()
            val res = scheduleApi.getSchedule(clientName, Clock.System.now())
            ResultWrapper.Success(res)
        } catch (e: Exception) {
            ResultWrapper.Error(mapError(e))
        }
    }
}