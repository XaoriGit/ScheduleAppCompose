package ru.xaori.schedule.presentation.screen.schedule.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.xaori.schedule.presentation.utils.RelativeTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun LastUpdatedDate(updatedDate: String) {
    Row {
        Text(
            "Обновлено ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            RelativeTimeFormatter.format(Instant.parse(updatedDate)),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}