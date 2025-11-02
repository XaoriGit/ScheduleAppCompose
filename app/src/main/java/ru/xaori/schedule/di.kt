package ru.xaori.schedule

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.xaori.schedule.data.dataModule
import ru.xaori.schedule.data.api.createHttpClient
import ru.xaori.schedule.presentation.viewmodel.viewModelModule
import ru.xaori.schedule.domain.domainModule
import ru.xaori.schedule.presentation.navigation.NavViewModel

val commonModule = module {
    single { createHttpClient() }
    viewModelOf(::NavViewModel)

    includes(viewModelModule)
    includes(dataModule)
    includes(domainModule)
}