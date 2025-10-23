package ru.xaori.schedule.data

import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.xaori.schedule.data.api.ClientChoiceApi
import ru.xaori.schedule.data.api.ScheduleApi
import ru.xaori.schedule.data.repository.ClientChoiceRepository
import ru.xaori.schedule.data.repository.ScheduleRepository
import ru.xaori.schedule.data.storage.ClientChoiceStorage

@OptIn(ExperimentalSettingsApi::class)
val DataModule = module {
    singleOf(::ScheduleApi)
    singleOf(::ClientChoiceApi)

    singleOf(::ScheduleRepository)
    singleOf(::ClientChoiceRepository)

    singleOf(::ClientChoiceStorage)
}