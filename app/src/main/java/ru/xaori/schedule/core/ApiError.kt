package ru.xaori.schedule.core

sealed class ApiError : Throwable() {
    object Network : ApiError() {
        private fun readResolve(): Any = Network
    }

    data class Server(val code: Int, override val message: String?) : ApiError()
    data class Unknown(val error: Throwable) : ApiError()
}
