package ru.xaori.schedule.core

sealed class UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>()
    object Loading : UIState<Nothing>()
    data class Error(val error: ApiError) : UIState<Nothing>()
}