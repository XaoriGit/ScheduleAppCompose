package ru.xaori.schedule.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.presentation.screen.onboarding.OnboardingScreen
import ru.xaori.schedule.presentation.screen.schedule.ScheduleScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(viewModel: NavViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val newUser by viewModel.newUser.collectAsState()
    val startDestination = if (newUser) Screen.Onboarding.route else Screen.Schedule.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = { navController.navigate(Screen.Schedule.route) }
            )
        }
        composable(Screen.Schedule.route) {
            ScheduleScreen(
                goToSettings = {
                    // TODO: Экран настроек
//                    navController.navigate(Screen.ClientChoice(true))
                }
            )
        }
    }
}