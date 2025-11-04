package ru.xaori.schedule.domain.model.schedule

data class ScheduleUiData(
    val scheduleData: ScheduleDataResponse,
    val isCached: Boolean
)