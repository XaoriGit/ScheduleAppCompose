package ru.xaori.schedule.presentation.state

enum class SnackbarType {
    SUCCESS, ERROR
}

data class SnackbarUiState(
    val isVisible: Boolean = false,
    val message: String = "",
    val type: SnackbarType = SnackbarType.ERROR,
    val actionLabel: String? = null
)