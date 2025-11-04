package ru.xaori.schedule.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import ru.xaori.schedule.domain.model.settings.AppThemeMode

class SettingsStore(
    private val dataStore: DataStore<Preferences>
) {
    private val clientKey = stringPreferencesKey("client_name")
    private val fcmTokenKey = stringPreferencesKey("fcm_token")
    private val appThemeKey = stringPreferencesKey("app_theme")


    suspend fun setClient(client: String) = dataStore.edit { it[clientKey] = client }
    suspend fun getClient(): String? = dataStore.data.firstOrNull()?.get(clientKey)


    suspend fun setFcmToken(token: String) = dataStore.edit { it[fcmTokenKey] = token }
    suspend fun getFcmToken(): String? = dataStore.data.firstOrNull()?.get(fcmTokenKey)


    suspend fun setAppTheme(themeMode: AppThemeMode) =
        dataStore.edit { it[appThemeKey] = themeMode.name }

    suspend fun getAppTheme(): AppThemeMode {
        val preferences = dataStore.data.first()
        val name = preferences[appThemeKey]
        return try {
            if (name != null) AppThemeMode.valueOf(name)
            else AppThemeMode.SYSTEM
        } catch (_: IllegalArgumentException) {
            AppThemeMode.SYSTEM
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }


}