package ru.xaori.schedule.presentation.viewmodel

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::ScheduleViewModel)
    viewModelOf(::ClientChoiceViewModel)
}