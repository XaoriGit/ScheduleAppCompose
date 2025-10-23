package ru.xaori.schedule.presentation.state

sealed class AppUiState {
    object Loading: AppUiState()
    object Success: AppUiState()
    object NewUser: AppUiState()
}