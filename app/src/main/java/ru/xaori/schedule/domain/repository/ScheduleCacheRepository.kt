package ru.xaori.schedule.domain.repository

import ru.xaori.schedule.domain.model.schedule.ScheduleDataResponse

interface ScheduleCacheRepository {
    suspend fun saveSchedule(data: ScheduleDataResponse)
    suspend fun getCachedSchedule(): ScheduleDataResponse?
    suspend fun clear()
}