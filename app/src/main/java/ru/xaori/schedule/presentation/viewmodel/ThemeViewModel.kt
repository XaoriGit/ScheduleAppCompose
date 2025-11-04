package ru.xaori.schedule.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.data.store.SettingsStore
import ru.xaori.schedule.domain.model.settings.AppThemeMode

class ThemeViewModel(
    private val settingsStore: SettingsStore
) : ViewModel() {
    private val _theme = MutableStateFlow(AppThemeMode.SYSTEM)
    val theme: StateFlow<AppThemeMode> = _theme

    init {
        viewModelScope.launch {
            _theme.value = settingsStore.getAppTheme()
        }
    }

    fun setTheme(mode: AppThemeMode) {
        viewModelScope.launch {
            settingsStore.setAppTheme(mode)
        }
        _theme.value = mode
    }
}