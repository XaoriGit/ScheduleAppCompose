package ru.xaori.schedule.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.presentation.screen.onboarding.OnboardingScreen
import ru.xaori.schedule.presentation.screen.schedule.ScheduleScreen
import ru.xaori.schedule.presentation.screen.settings.SettingsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(viewModel: NavViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val newUser by viewModel.newUser.collectAsState()
    val startDestination = when (newUser) {
        null -> return
        true -> Screen.Onboarding.route
        false -> Screen.Schedule.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideInHorizontally { it } + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally { -it / 3 } + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally { -it } + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally { it / 3 } + fadeOut()
        }
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = { navController.navigate(Screen.Schedule.route) }
            )
        }
        composable(Screen.Schedule.route) {
            ScheduleScreen(
                goToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen { isRefresh ->
                if (isRefresh) {
                    navController.navigate(Screen.Schedule.route)
                } else {
                    navController.popBackStack(
                        route = Screen.Schedule.route,
                        inclusive = false
                    )
                }

            }
        }
    }
}