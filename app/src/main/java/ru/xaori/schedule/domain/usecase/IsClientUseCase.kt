package ru.xaori.schedule.domain.usecase

import ru.xaori.schedule.domain.repository.ClientChoiceRepository

class IsClientUseCase(
    private val clientChoiceRepository: ClientChoiceRepository
) {
    suspend operator fun invoke(): Boolean {
        return clientChoiceRepository.isClient()
    }
}