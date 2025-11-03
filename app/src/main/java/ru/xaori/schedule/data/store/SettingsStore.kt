package ru.xaori.schedule.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull

class SettingsStore(
    private val dataStore: DataStore<Preferences>
) {
    private val clientKey = stringPreferencesKey("client_name")
    private val fcmTokenKey = stringPreferencesKey("fcm_token")

    suspend fun setClient(client: String) = dataStore.edit { it[clientKey] = client }
    suspend fun getClient(): String? = dataStore.data.firstOrNull()?.get(clientKey)

    suspend fun setFcmToken(token: String) = dataStore.edit { it[fcmTokenKey] = token }
    suspend fun getFcmToken(): String? = dataStore.data.firstOrNull()?.get(fcmTokenKey)


}