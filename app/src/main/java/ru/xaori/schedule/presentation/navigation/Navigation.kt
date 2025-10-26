package ru.xaori.schedule.presentation.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import ru.xaori.schedule.presentation.screen.clientChoice.ClientChoiceScreen
import ru.xaori.schedule.presentation.screen.schedule.ScheduleScreen
import ru.xaori.schedule.presentation.screen.start.StartScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(viewModel: NavViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val newUser by viewModel.newUser.collectAsState()
    val startDestination = if (newUser) Screen.Start.route else Screen.Schedule.route

    LaunchedEffect(newUser) {
        Log.d("APP", newUser.toString())
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.Start.route) {
            StartScreen {
                navController.navigate(Screen.ClientChoice(false)) {
                    popUpTo(Screen.Start.route) { inclusive = true }
                }
            }
        }
        composable(Screen.Schedule.route) {
            ScheduleScreen(
                goToSettings = {
                    navController.navigate(Screen.ClientChoice(true))
                }
            )
        }
        composable<Screen.ClientChoice> { backStackEntry ->
            val clientChoice: Screen.ClientChoice = backStackEntry.toRoute()
            ClientChoiceScreen(clientChoice.showCancelButton, {
                navController.popBackStack()
            }, {
                navController.navigate(Screen.Schedule.route) {
                    popUpTo(Screen.ClientChoice(false)) { inclusive = true }
                }
            })
        }
    }
}