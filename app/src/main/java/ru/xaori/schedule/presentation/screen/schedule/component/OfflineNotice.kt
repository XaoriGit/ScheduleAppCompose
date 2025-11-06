package ru.xaori.schedule.presentation.screen.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.R

@Composable
fun OfflineNotice() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryFixed)
            .padding(8.dp, 12.dp, 16.dp, 12.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_lightbulb_star),
            contentDescription = "",
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.onPrimaryFixedVariant
        )
        Column {
            Text(
                "Нет доступа к расписанию",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Text(
                "Подключитесь к интернету чтобы получить актуальное расписание занятий",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}