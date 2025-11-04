package ru.xaori.schedule.domain.model.schedule

data class ScheduleResult(
    val data: ScheduleDataResponse,
    val isCached: Boolean
)