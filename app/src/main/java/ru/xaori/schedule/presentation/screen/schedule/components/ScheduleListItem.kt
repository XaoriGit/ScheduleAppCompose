package ru.xaori.schedule.presentation.screen.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.domain.model.Lesson


@Composable
fun ScheduleListItem(
    lesson: Lesson
) {
    Card(
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surfaceContainer
        ), shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 12.dp),
        ) {
            lesson.items.forEachIndexed { index, (title, type, partner, location) ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Если элемент второй то добавляем разделитель
                    if (index == 1) {
                        HorizontalDivider(
                            Modifier.padding(top = 8.dp), 1.dp, MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${lesson.number} пара",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            lesson.time,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            InfoChip(
                                type,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.onPrimary

                            )
                            if (location.isNotEmpty()) {
                                InfoChip(
                                    location,
                                    MaterialTheme.colorScheme.secondary,
                                    MaterialTheme.colorScheme.onSecondary

                                )
                            }
                        }
                        InfoChip(
                            partner,
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.onTertiary

                        )
                    }
                }
            }
        }
    }
}