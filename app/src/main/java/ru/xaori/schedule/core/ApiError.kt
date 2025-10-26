package ru.xaori.schedule.core

sealed class ApiError : Throwable() {
    object Network : ApiError() {
        private fun readResolve(): Any = Network
    }

    object Timeout : ApiError() {
        private fun readResolve(): Any = Timeout
    }

    data class Server(val code: Int, override val message: String?) : ApiError()
    data class Unknown(val error: Throwable) : ApiError()
}
