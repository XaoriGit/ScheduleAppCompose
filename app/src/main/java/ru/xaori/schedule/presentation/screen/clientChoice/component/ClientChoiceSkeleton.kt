package ru.xaori.schedule.presentation.screen.clientChoice.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.presentation.common.ShimmerAnimation

@Composable
fun ClientChoiceSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(3) {
            ShimmerAnimation(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}