package ru.xaori.schedule.data.repository

import ru.xaori.schedule.data.api.ClientChoiceApi
import ru.xaori.schedule.domain.model.ClientChoiceResponse
import ru.xaori.schedule.data.storage.ClientChoiceStorage
import ru.xaori.schedule.presentation.state.ResultWrapper
import ru.xaori.schedule.presentation.state.mapError

class ClientChoiceRepository(
    private val clientChoiceApi: ClientChoiceApi,
    private val clientChoiceStorage: ClientChoiceStorage
) {
    suspend fun getClients(): ResultWrapper<ClientChoiceResponse> {
        return try {
            val res = clientChoiceApi.getClients()
            ResultWrapper.Success(res)
        } catch (e: Throwable) {
            ResultWrapper.Error(mapError(e))
        }
    }

    suspend fun getClient(): String {
        return clientChoiceStorage.getClient() ?: "Клиент не найден"
    }

    suspend fun setClient(value: String) = clientChoiceStorage.setClient(value)
}