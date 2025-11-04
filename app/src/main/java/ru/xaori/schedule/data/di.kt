package ru.xaori.schedule.data

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.xaori.schedule.data.api.ClientChoiceApi
import ru.xaori.schedule.data.api.NotificationApi
import ru.xaori.schedule.data.api.ScheduleApi
import ru.xaori.schedule.data.repository.ClientChoiceRepositoryImpl
import ru.xaori.schedule.data.repository.NotificationRepositoryImpl
import ru.xaori.schedule.data.repository.ScheduleCacheRepositoryImpl
import ru.xaori.schedule.data.repository.ScheduleRepositoryImpl
import ru.xaori.schedule.data.repository.SettingsRepositoryImpl
import ru.xaori.schedule.data.store.SettingsStore
import ru.xaori.schedule.data.store.dataStoreModule
import ru.xaori.schedule.domain.repository.ClientChoiceRepository
import ru.xaori.schedule.domain.repository.NotificationRepository
import ru.xaori.schedule.domain.repository.ScheduleCacheRepository
import ru.xaori.schedule.domain.repository.ScheduleRepository
import ru.xaori.schedule.domain.repository.SettingsRepository

val dataModule = module {
    includes(dataStoreModule)

    singleOf(::ScheduleApi)
    singleOf(::ClientChoiceApi)
    singleOf(::NotificationApi)

    singleOf(::ScheduleRepositoryImpl) {bind<ScheduleRepository>()}
    singleOf(::SettingsRepositoryImpl) {bind<SettingsRepository>()}
    singleOf(::ClientChoiceRepositoryImpl) {bind<ClientChoiceRepository>()}
    singleOf(::ScheduleCacheRepositoryImpl) {bind<ScheduleCacheRepository>()}
    singleOf(::NotificationRepositoryImpl) {bind<NotificationRepository>()}

    singleOf(::SettingsStore)
}