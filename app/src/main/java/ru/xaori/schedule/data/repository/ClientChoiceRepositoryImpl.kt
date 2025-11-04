package ru.xaori.schedule.data.repository

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import ru.xaori.schedule.core.ApiError
import ru.xaori.schedule.data.api.ClientChoiceApi
import ru.xaori.schedule.domain.model.clientChoice.ClientChoiceResponse
import ru.xaori.schedule.domain.repository.ClientChoiceRepository
import ru.xaori.schedule.domain.repository.SettingsRepository

class ClientChoiceRepositoryImpl(
    private val clientChoiceApi: ClientChoiceApi,
    private val settingsRepository: SettingsRepository
): ClientChoiceRepository {
    override suspend fun getClients(): Result<ClientChoiceResponse> {
        return try {
            val res = clientChoiceApi.getClients()
            Result.success(res)
        } catch (_: IOException) {
            Result.failure(ApiError.Network)
        } catch (e: ClientRequestException) {
            Result.failure(ApiError.Server(e.response.status.value, e.message))
        } catch (e: ServerResponseException) {
            Result.failure(ApiError.Server(e.response.status.value, e.message))
        } catch (e: Throwable) {
            Result.failure(ApiError.Unknown(e))
        }
    }

    override suspend fun getClient(): String {
        return settingsRepository.getClient() ?: "Клиент не найден"
    }

    override suspend fun setClient(value: String) = settingsRepository.setClient(value)

    override suspend fun isClient(): Boolean {
        val clientString = settingsRepository.getClient()
        return !clientString.isNullOrEmpty()
    }
}