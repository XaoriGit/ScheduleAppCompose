package ru.xaori.schedule.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Schedule : Screen("schedule")
}