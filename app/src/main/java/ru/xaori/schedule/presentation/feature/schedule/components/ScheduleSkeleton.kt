package ru.xaori.schedule.presentation.feature.schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.presentation.common.ShimmerAnimation

@Composable
fun ScheduleSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerAnimation(
                modifier = Modifier
                    .height(36.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            repeat(5) {
                ShimmerAnimation(
                    modifier = Modifier
                        .height(36.dp)
                        .width(72.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

        }
        repeat(4) {
            ShimmerAnimation(
                modifier = Modifier
                    .height(144.dp)
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}