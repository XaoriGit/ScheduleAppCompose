package ru.xaori.schedule.data.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
private val Context.appDataStore by preferencesDataStore(name = "app_prefs")

val dataStoreModule = module {
    single<androidx.datastore.core.DataStore<Preferences>> {
        androidContext().appDataStore
    }
}
