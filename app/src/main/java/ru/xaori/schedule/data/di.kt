package ru.xaori.schedule.data

import com.russhwolf.settings.ExperimentalSettingsApi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.xaori.schedule.data.api.ClientChoiceApi
import ru.xaori.schedule.data.api.ScheduleApi
import ru.xaori.schedule.data.repository.ClientChoiceRepositoryImpl
import ru.xaori.schedule.data.repository.ScheduleRepositoryImpl
import ru.xaori.schedule.data.storage.ClientChoiceStorage
import ru.xaori.schedule.domain.repository.ClientChoiceRepository
import ru.xaori.schedule.domain.repository.ScheduleRepository

@OptIn(ExperimentalSettingsApi::class)
val dataModule = module {
    singleOf(::ScheduleApi)
    singleOf(::ClientChoiceApi)

    singleOf(::ScheduleRepositoryImpl) {bind<ScheduleRepository>()}
    singleOf(::ClientChoiceRepositoryImpl) {bind<ClientChoiceRepository>()}


    singleOf(::ClientChoiceStorage)
}