package ru.xaori.schedule.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.xaori.schedule.data.store.SettingsStore
import ru.xaori.schedule.domain.model.settings.AppThemeMode
import ru.xaori.schedule.presentation.state.DialogSettingsState

class SettingsViewModel(
    private val themeViewModel: ThemeViewModel,
    private val settingsStore: SettingsStore
) : ViewModel() {
    val theme: StateFlow<AppThemeMode> = themeViewModel.theme

    private val _dialogState = MutableStateFlow<DialogSettingsState>(DialogSettingsState.None)
    val dialogState: StateFlow<DialogSettingsState> = _dialogState

    fun showDialog(dialog: DialogSettingsState) {
        _dialogState.value = dialog
    }

    fun dismissDialog() {
        _dialogState.value = DialogSettingsState.None
    }

    fun setTheme(mode: AppThemeMode) {
        themeViewModel.setTheme(mode)
    }

    fun openNotificationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    }

    fun clearStore(onComplete: () -> Unit) {
        viewModelScope.launch {
            settingsStore.clearAll()
            dismissDialog()
            onComplete()
        }
    }
}