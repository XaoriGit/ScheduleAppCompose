package ru.xaori.schedule.presentation.state


sealed class AppError {
    object NoInternet : AppError()
    data class HttpError(val code: Int, val message: String?) : AppError()
    object NotFound : AppError()
    data class Unknown(val throwable: Throwable) : AppError()
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val error: AppError) : ResultWrapper<Nothing>()
}