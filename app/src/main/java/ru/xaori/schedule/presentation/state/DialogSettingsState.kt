package ru.xaori.schedule.presentation.state

sealed class DialogSettingsState {
    object None : DialogSettingsState()
    object ClearData : DialogSettingsState()
    object ThemePicker : DialogSettingsState()
    object Notification : DialogSettingsState()
}