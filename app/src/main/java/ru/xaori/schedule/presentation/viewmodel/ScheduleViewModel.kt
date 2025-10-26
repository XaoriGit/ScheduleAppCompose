package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.core.UIState
import ru.xaori.schedule.domain.model.ScheduleDataResponse
import ru.xaori.schedule.domain.repository.ScheduleRepository

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState<ScheduleDataResponse>>(UIState.Loading)
    val uiState: StateFlow<UIState<ScheduleDataResponse>> = _uiState



    init {
        getSchedule()
    }

    fun getSchedule() {
        _uiState.value = UIState.Loading
        viewModelScope.launch {
            scheduleRepository.getSchedule().fold(
                onSuccess = { data -> _uiState.value = UIState.Success(data) },
                onFailure = { throwable ->
                    val apiError = throwable as? ApiError ?: ApiError.Unknown(throwable)
                    _uiState.value = UIState.Error(apiError)
                }
            )
        }
    }
}