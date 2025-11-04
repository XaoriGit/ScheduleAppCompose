package ru.xaori.schedule.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.presentation.state.AnimatedAppBarStatus

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AnimatedAppBar(
    title: String,
    status: AnimatedAppBarStatus,
    goToSettings: () -> Unit = {},
    leftContent: @Composable (RowScope.() -> Unit) = {}
) {
    val currentStatus by rememberUpdatedState(newValue = status)
    val color by animateColorAsState(
        targetValue =  when (status) {
            is AnimatedAppBarStatus.Loading -> MaterialTheme.colorScheme.outline
            is AnimatedAppBarStatus.SubTitle -> MaterialTheme.colorScheme.primary
            is AnimatedAppBarStatus.SubTitleError -> MaterialTheme.colorScheme.error
        },
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 4.dp)
            )
            AnimatedContent(targetState = status) { currentStatus ->
                when (currentStatus) {
                    is AnimatedAppBarStatus.Loading -> {
                        Text(
                            "Загрузка...",
                            style = MaterialTheme.typography.labelLarge,
                            color = color
                        )
                    }
                    is AnimatedAppBarStatus.SubTitle -> {
                        Text(
                            currentStatus.subTitle,
                            style = MaterialTheme.typography.labelLarge,
                            color = color,
                            modifier = Modifier.clickable(
                                onClick = goToSettings
                            )
                        )
                    }
                    is AnimatedAppBarStatus.SubTitleError -> {
                        Text(
                            currentStatus.subTitle,
                            style = MaterialTheme.typography.labelLarge,
                            color = color
                        )
                    }
                }
            }
        }
        leftContent()
    }
}