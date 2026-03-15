package ru.xaori.schedule.presentation.screen.settings

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.BuildConfig
import ru.xaori.schedule.R
import ru.xaori.schedule.presentation.screen.clientChoice.ClientChoiceSheetContent
import ru.xaori.schedule.presentation.screen.settings.component.ConfirmDialog
import ru.xaori.schedule.presentation.screen.settings.component.SettingsAppBar
import ru.xaori.schedule.presentation.screen.settings.component.SettingsItem
import ru.xaori.schedule.presentation.screen.settings.component.ThemePickerDialog
import ru.xaori.schedule.presentation.state.DialogSettingsState
import ru.xaori.schedule.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    goToSchedule: (isRefresh: Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val dialogState by viewModel.dialogState.collectAsState()
    val theme by viewModel.theme.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(0.dp, 8.dp),
    ) {
        SettingsAppBar { goToSchedule(false) }
        Column {
            SettingsItem(
                title = "Тема приложения",
                description = theme.title,
                leftContent = {
                    Icon(
                        painterResource(R.drawable.ic_palette),
                        contentDescription = "palette",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                onClick = {
                    viewModel.showDialog(DialogSettingsState.ThemePicker)
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                })
            SettingsItem(
                title = "Очистить данные",
                description = "Удаляет данные приложения",
                leftContent = {
                    Icon(
                        painterResource(R.drawable.ic_delete),
                        contentDescription = "palette",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                onClick = {
                    viewModel.showDialog(DialogSettingsState.ClearData)
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                }
            )
            HorizontalDivider()
            SettingsItem(
                title = "Расписание", description = "Здесь можно выбрать расписание",
                onClick = {
                    showBottomSheet = true
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                }
            )
            SettingsItem(
                title = "Push-уведомления", description = "Управляет отправкой уведомлений",
                onClick = {
                    viewModel.openNotificationSettings(context = context)
                }
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
        )
    }

    when (dialogState) {
        DialogSettingsState.ClearData -> ConfirmDialog(
            title = "Удаление данных",
            description = "Вы действительно хотите удалить данные без возможности восстановить",
            onConfirm = {
                activity?.let { act ->
                    viewModel.clearStore {
                        act.finishAffinity()
                    }
                }
            },
            onDismiss = viewModel::dismissDialog
        )

        DialogSettingsState.ThemePicker -> {
            ThemePickerDialog(
                currentTheme = theme,
                onThemeSelected = viewModel::setTheme,
                onDismiss = viewModel::dismissDialog
            )
        }

        else -> {}
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                }
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            tonalElevation = 3.dp
        ) {
            ClientChoiceSheetContent(
                onClientChosen = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                        goToSchedule(true)
                    }
                })
        }
    }
}