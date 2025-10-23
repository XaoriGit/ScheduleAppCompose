package ru.xaori.schedule

import android.content.Context
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.xaori.schedule.data.DataModule
import ru.xaori.schedule.data.api.createHttpClient
import ru.xaori.schedule.presentation.viewmodel.ViewModelModule
import ru.xaori.schedule.data.storage.provideSettings

@OptIn(ExperimentalSettingsApi::class)
val CommonModule = module {
    single<SuspendSettings> {
        val context: Context = androidContext()
        provideSettings(context)
    }
    single { createHttpClient() }

    viewModelOf(::AppViewModel)

    includes(ViewModelModule)
    includes(DataModule)
}