package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.presentation.state.SnackbarType
import ru.xaori.schedule.presentation.state.SnackbarUiState

class SnackbarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SnackbarUiState())
    val uiState: StateFlow<SnackbarUiState> = _uiState.asStateFlow()

    fun showSnackbar(
        message: String,
        type: SnackbarType = SnackbarType.ERROR,
        actionLabel: String? = null,
        duration: Long = 3000L
    ) {
        if (!uiState.value.isVisible) {
            _uiState.value = SnackbarUiState(
                isVisible = true,
                message = message,
                type = type,
                actionLabel = actionLabel
            )
            viewModelScope.launch {
                delay(duration)
                hideSnackbar()
            }
        }
    }

    fun hideSnackbar() {
        _uiState.value = _uiState.value.copy(isVisible = false)
    }
}