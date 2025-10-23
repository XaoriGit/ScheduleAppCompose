package ru.xaori.schedule.presentation.state

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import java.nio.channels.UnresolvedAddressException

fun isNoInternetError(e: Throwable): Boolean =
    e is IOException || e is UnresolvedAddressException

fun mapError(e: Throwable): AppError {
    return when {
        isNoInternetError(e) -> AppError.NoInternet

        e is ClientRequestException -> {
            if (e.response.status.value == 404) {
                AppError.NotFound
            } else {
                AppError.HttpError(e.response.status.value, e.message)
            }
        }

        e is RedirectResponseException -> AppError.HttpError(e.response.status.value, e.message)
        e is ClientRequestException -> AppError.HttpError(e.response.status.value, e.message)
        e is ServerResponseException -> AppError.HttpError(e.response.status.value, e.message)

        else -> AppError.Unknown(e)
    }
}