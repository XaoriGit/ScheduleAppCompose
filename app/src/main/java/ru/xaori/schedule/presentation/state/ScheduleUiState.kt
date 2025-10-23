package ru.xaori.schedule.presentation.state

import ru.xaori.schedule.domain.model.ScheduleDataResponse

sealed class ScheduleUiState {
    object Loading: ScheduleUiState()
    data class Success(val scheduleData: ScheduleDataResponse): ScheduleUiState()
    data class Error(val detail: String): ScheduleUiState()
}