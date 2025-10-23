package ru.xaori.schedule.data.storage

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import ru.xaori.schedule.domain.model.ClientChoiceConstants

@OptIn(ExperimentalSettingsApi::class)
class ClientChoiceStorage(private val settings: SuspendSettings) {
    suspend fun setClient(client: String) = settings.putString(ClientChoiceConstants.CLIENT, client)
    suspend fun getClient(): String? = settings.getStringOrNull(ClientChoiceConstants.CLIENT)
}