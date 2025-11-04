package ru.xaori.schedule.presentation.screen.clientChoice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.presentation.viewmodel.ClientChoiceViewModel

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientChoiceBottomSheetHost(
    viewModel: ClientChoiceViewModel = koinViewModel(),
    onClientChosen: () -> Unit,
    content: @Composable (onVisible: (value: Boolean) -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val showBottomSheet by viewModel.showBottomSheet.collectAsState()


    Box {
        content {
            viewModel.onShowBottomSheet(it)
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                        viewModel.onShowBottomSheet(false)
                    }
                },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                tonalElevation = 3.dp
            ) {
                ClientChoiceSheetContent(
                    onClientChosen = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onClientChosen()
                            viewModel.onShowBottomSheet(false)
                        }
                    }
                )
            }
        }
    }
}