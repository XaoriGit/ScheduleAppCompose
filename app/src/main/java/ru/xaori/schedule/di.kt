package ru.xaori.schedule

import android.content.Context
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.xaori.schedule.data.dataModule
import ru.xaori.schedule.data.api.createHttpClient
import ru.xaori.schedule.presentation.viewmodel.viewModelModule
import ru.xaori.schedule.data.storage.provideSettings
import ru.xaori.schedule.domain.domainModule
import ru.xaori.schedule.presentation.navigation.NavViewModel

@OptIn(ExperimentalSettingsApi::class)
val commonModule = module {
    single<SuspendSettings> {
        val context: Context = androidContext()
        provideSettings(context)
    }
    single { createHttpClient() }
    viewModelOf(::NavViewModel)

    includes(viewModelModule)
    includes(dataModule)
    includes(domainModule)
}