package ru.xaori.schedule.domain.model

data class ScheduleResult(
    val data: ScheduleDataResponse,
    val isCached: Boolean
)