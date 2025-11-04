package ru.xaori.schedule.domain.repository

import ru.xaori.schedule.domain.model.schedule.ScheduleResult

interface ScheduleRepository{
    suspend fun getSchedule(): Result<ScheduleResult>
}