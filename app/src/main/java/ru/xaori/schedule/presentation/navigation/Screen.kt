package ru.xaori.schedule.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Schedule : Screen("schedule")
    object Settings : Screen("settings")
}