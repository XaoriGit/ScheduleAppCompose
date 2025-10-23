package ru.xaori.schedule.presentation.screen.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.domain.model.ScheduleDay

@Composable
fun ScheduleList(
    schedule: List<ScheduleDay>,
    lastUpdate: String,
    pageState: PagerState,
) {
    HorizontalPager(
        state = pageState,
        pageSpacing = 16.dp,
        beyondViewportPageCount = 2,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->
        if (schedule[page].lessons.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 12.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(schedule[page].lessons) { lesson ->
                    ScheduleListItem(lesson)
                }
                item {
                    LastUpdatedDate(lastUpdate)
                }
            }
        } else {
            ScheduleEmptyListItem()
        }
    }
}