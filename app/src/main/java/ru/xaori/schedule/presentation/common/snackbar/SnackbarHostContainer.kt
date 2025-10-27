package ru.xaori.schedule.presentation.common.snackbar

import CustomSnackbarHostWithState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.presentation.viewmodel.SnackbarViewModel

@Composable
fun SnackbarHostContainer(
    viewModel: SnackbarViewModel = koinViewModel(),
    content: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        CustomSnackbarHostWithState(
            uiState = uiState,
            modifier = Modifier.align(Alignment.BottomCenter),
            onAction = {},
            onHide = { viewModel.hideSnackbar() }
        )
    }
}
