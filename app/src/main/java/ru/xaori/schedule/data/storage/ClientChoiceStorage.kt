package ru.xaori.schedule.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import ru.xaori.schedule.domain.model.ClientChoiceConstants


class ClientChoiceStorage(
    private val dataStore: DataStore<Preferences>
) {
    private val clientKey = stringPreferencesKey(ClientChoiceConstants.CLIENT)

    suspend fun setClient(client: String) {
        dataStore.edit { prefs ->
            prefs[clientKey] = client
        }
    }

    suspend fun getClient(): String? {
        val prefs = dataStore.data.firstOrNull() ?: return null
        return prefs[clientKey]
    }
}