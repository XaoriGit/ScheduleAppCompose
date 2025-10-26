package ru.xaori.schedule.domain

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.xaori.schedule.domain.usecase.IsClientUseCase

val domainModule = module {
    factoryOf(::IsClientUseCase)
}