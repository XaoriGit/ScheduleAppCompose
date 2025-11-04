package ru.xaori.schedule.presentation.screen.settings

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.union
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.BuildConfig
import ru.xaori.schedule.R
import ru.xaori.schedule.presentation.screen.settings.component.ConfirmDialog
import ru.xaori.schedule.presentation.screen.settings.component.SettingsAppBar
import ru.xaori.schedule.presentation.screen.settings.component.SettingsItem
import ru.xaori.schedule.presentation.screen.settings.component.ThemePickerDialog
import ru.xaori.schedule.presentation.state.DialogSettingsState
import ru.xaori.schedule.presentation.viewmodel.SettingsViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    goToSchedule: () -> Unit
) {
    val activity = LocalContext.current as? Activity


    val dialogState by viewModel.dialogState.collectAsState()
    val theme by viewModel.theme.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(0.dp, 8.dp),
    ) {
        SettingsAppBar { goToSchedule() }
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
                onClick = { viewModel.showDialog(DialogSettingsState.ThemePicker) })
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
                onClick = { viewModel.showDialog(DialogSettingsState.ClearData) }
            )
            HorizontalDivider()
            SettingsItem(
                title = "Расписание", description = "Пока пусть"
            )
            SettingsItem(
                title = "Push-уведомления", description = "Управляет отправкой уведомлений",
                rightContent = {
                    Switch(
                        true,
                        onCheckedChange = {}
                    )
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
                        .union(WindowInsets.ime)
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

        DialogSettingsState.Notification -> ConfirmDialog(
            title = "Подтверждение",
            description = "Вы точно хотите отключить уведомления",
            onConfirm = {},
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
}