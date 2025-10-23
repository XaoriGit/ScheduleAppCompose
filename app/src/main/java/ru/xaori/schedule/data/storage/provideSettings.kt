package ru.xaori.schedule.data.storage

import android.content.Context
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings

@OptIn(ExperimentalSettingsApi::class)
fun provideSettings(context: Context): SuspendSettings {
    val delegate = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate).toSuspendSettings()
}