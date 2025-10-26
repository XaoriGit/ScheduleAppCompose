package ru.xaori.schedule.data.repository

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.data.api.ClientChoiceApi
import ru.xaori.schedule.domain.model.ClientChoiceResponse
import ru.xaori.schedule.data.storage.ClientChoiceStorage
import ru.xaori.schedule.domain.repository.ClientChoiceRepository

class ClientChoiceRepositoryImpl(
    private val clientChoiceApi: ClientChoiceApi,
    private val clientChoiceStorage: ClientChoiceStorage
): ClientChoiceRepository {
    override suspend fun getClients(): Result<ClientChoiceResponse> {
        return try {
            val res = clientChoiceApi.getClients()
            Result.success(res)
        } catch (_: IOException) {
            Result.failure(ApiError.Network)
        } catch (_: HttpRequestTimeoutException) {
            Result.failure(ApiError.Timeout)
        } catch (e: ClientRequestException) {
            Result.failure(ApiError.Server(e.response.status.value, e.message))
        } catch (e: ServerResponseException) {
            Result.failure(ApiError.Server(e.response.status.value, e.message))
        } catch (e: Throwable) {
            Result.failure(ApiError.Unknown(e))
        }
    }

    override suspend fun getClient(): String {
        return clientChoiceStorage.getClient() ?: "Клиент не найден"
    }

    override suspend fun setClient(value: String) = clientChoiceStorage.setClient(value)

    override suspend fun isClient(): Boolean {
        val clientString = clientChoiceStorage.getClient()
        return !clientString.isNullOrEmpty()
    }

}