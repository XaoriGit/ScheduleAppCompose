package ru.xaori.schedule.presentation.screen.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.presentation.screen.clientChoice.ClientChoiceBottomSheetHost
import ru.xaori.schedule.presentation.screen.onboarding.component.ClientChoiceStep
import ru.xaori.schedule.presentation.screen.onboarding.component.NotificationsStep
import ru.xaori.schedule.presentation.state.OnboardingStep
import ru.xaori.schedule.presentation.viewmodel.OnboardingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(), onFinish: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()

    ClientChoiceBottomSheetHost(
        onClientChosen = { viewModel.chooseClient() }) { onVisible ->
        AnimatedContent(uiState) {
            when (it.currentStep) {
                OnboardingStep.CLIENT_CHOICE -> ClientChoiceStep { onVisible(true) }
                OnboardingStep.NOTIFICATIONS -> NotificationsStep {
                    viewModel.goToStep(OnboardingStep.DONE)
                }

                OnboardingStep.DONE -> onFinish()
            }
        }
    }
}
