package ru.xaori.schedule.domain.repository

import ru.xaori.schedule.domain.model.ScheduleDataResponse
import ru.xaori.schedule.domain.model.ScheduleResult

interface ScheduleRepository{
    suspend fun getSchedule(): Result<ScheduleResult>
}