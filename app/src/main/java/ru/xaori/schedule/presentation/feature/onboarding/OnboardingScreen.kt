package ru.xaori.schedule.presentation.feature.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.presentation.feature.clientChoice.ClientChoiceSheetContent
import ru.xaori.schedule.presentation.feature.onboarding.component.ClientChoiceStep
import ru.xaori.schedule.presentation.feature.onboarding.component.NotificationsStep
import ru.xaori.schedule.presentation.state.OnboardingStep
import ru.xaori.schedule.presentation.viewmodel.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    onFinish: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()

    var showClientChoiceSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    AnimatedContent(uiState) {
        when (it.currentStep) {
            OnboardingStep.CLIENT_CHOICE -> ClientChoiceStep { showClientChoiceSheet = true }
            OnboardingStep.NOTIFICATIONS -> NotificationsStep({
                viewModel.goToStep(
                    OnboardingStep.DONE
                )
            }, viewModel::onPermissionResult)

            OnboardingStep.DONE -> onFinish()
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
                    viewModel.chooseClient()
                }
            )
        }
    }
}