package ru.xaori.schedule.presentation.state

enum class OnboardingStep {
    CLIENT_CHOICE,
    NOTIFICATIONS,
    DONE
}

data class OnboardingState(
    val currentStep: OnboardingStep = OnboardingStep.CLIENT_CHOICE,
    val clientChosen: Boolean = false,
    val notificationsEnabled: Boolean = false
)