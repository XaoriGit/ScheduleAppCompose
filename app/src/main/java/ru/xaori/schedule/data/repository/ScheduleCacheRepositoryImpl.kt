package ru.xaori.schedule.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import ru.xaori.schedule.domain.model.schedule.ScheduleDataResponse
import ru.xaori.schedule.domain.repository.ScheduleCacheRepository

class ScheduleCacheRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : ScheduleCacheRepository {

    private val scheduleKey = stringPreferencesKey("cached_schedule")

    override suspend fun saveSchedule(data: ScheduleDataResponse) {
        val json = Json.encodeToString(data)
        dataStore.edit { prefs -> prefs[scheduleKey] = json }
    }

    override suspend fun getCachedSchedule(): ScheduleDataResponse? {
        val prefs = dataStore.data.firstOrNull() ?: return null
        val json = prefs[scheduleKey] ?: return null
        return runCatching { Json.decodeFromString<ScheduleDataResponse>(json) }.getOrNull()
    }

    override suspend fun clear() {
        dataStore.edit { it.remove(scheduleKey) }
    }
}