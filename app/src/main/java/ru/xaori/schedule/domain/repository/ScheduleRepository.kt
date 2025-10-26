package ru.xaori.schedule.domain.repository

import ru.xaori.schedule.domain.model.ScheduleDataResponse

interface ScheduleRepository{
    suspend fun getSchedule(): Result<ScheduleDataResponse>
}