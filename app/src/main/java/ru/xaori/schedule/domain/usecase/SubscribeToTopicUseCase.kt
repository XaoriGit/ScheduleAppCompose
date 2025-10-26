package ru.xaori.schedule.domain.usecase

import ru.xaori.schedule.domain.repository.NotificationRepository

class SubscribeToTopicUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(topic: String): Result<Unit> {
        return notificationRepository.subscribeToTopic(topic)
    }
}
