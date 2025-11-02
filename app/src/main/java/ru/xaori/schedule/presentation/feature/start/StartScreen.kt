package ru.xaori.schedule.presentation.feature.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.xaori.schedule.R
import ru.xaori.schedule.presentation.feature.clientChoice.ClientChoiceSheetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    goToSchedule: () -> Unit,
) {
    var showClientChoiceSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        modifier = Modifier.padding(16.dp, 8.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_education),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Добро пожаловать!",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Сначала нужно сделать важный выбор",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(
            onClick = { showClientChoiceSheet = true },
            contentPadding = PaddingValues(16.dp, 12.dp),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Выбрать расписание",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

    if (showClientChoiceSheet) {
        ModalBottomSheet(
            onDismissRequest = { showClientChoiceSheet = false },
            sheetState = sheetState,
        ) {
            ClientChoiceSheetContent(
                onClientChosen = {
                    showClientChoiceSheet = false
                    goToSchedule()
                }
            )
        }
    }
}