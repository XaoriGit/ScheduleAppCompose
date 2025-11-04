package ru.xaori.schedule.presentation.screen.schedule.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.domain.model.ScheduleDay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WeekDaysRow(
    selectedIndex: Int,
    days: List<ScheduleDay>,
    onDayClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        listState.animateScrollToItem(selectedIndex)
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(days) { index, day ->
            val isSelected = index == selectedIndex

            val containerColor by animateColorAsState(
                targetValue = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.surfaceContainer,
                label = "containerColor"
            )

            val textColor by animateColorAsState(
                targetValue = if (isSelected)
                    MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                label = "textColor"
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = containerColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .clickable { onDayClick(index) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "${if (isSelected) day.weekDayEnum.longDay else day.weekDayEnum.shortDay}, ",
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = day.dayOfMonth.toString(),
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
