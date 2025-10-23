package ru.xaori.schedule.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ru.xaori.schedule.presentation.screen.clientChoice.ClientChoiceScreen
import ru.xaori.schedule.presentation.screen.schedule.ScheduleScreen
import ru.xaori.schedule.presentation.screen.start.StartScreen

@Composable
fun Navigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
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
                },
                goToStart = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(Screen.Schedule.route) { inclusive = true }
                    }
                })
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