package ru.xaori.schedule.presentation.screen.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.domain.model.settings.AppThemeMode

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ThemePickerDialog(
    currentTheme: AppThemeMode,
    onThemeSelected: (AppThemeMode) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "Тема",
                    style = MaterialTheme.typography.titleLargeEmphasized,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Column {
                    AppThemeMode.entries.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onThemeSelected(item)
                                    onDismiss()
                                }
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            RadioButton(
                                selected = currentTheme == item,
                                onClick = {
                                    onThemeSelected(item)
                                    onDismiss()
                                }
                            )
                            Text(
                                item.title,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
