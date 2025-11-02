package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.xaori.schedule.presentation.state.OnboardingState
import ru.xaori.schedule.presentation.state.OnboardingStep

class OnboardingViewModel : ViewModel() {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state

    fun chooseClient() {
        _state.value = _state.value.copy(clientChosen = true, currentStep = OnboardingStep.NOTIFICATIONS)
    }

    fun enableNotifications() {
        _state.value = _state.value.copy(notificationsEnabled = true, currentStep = OnboardingStep.DONE)
    }

    fun goToStep(step: OnboardingStep) {
        _state.value = _state.value.copy(currentStep = step)
    }
}