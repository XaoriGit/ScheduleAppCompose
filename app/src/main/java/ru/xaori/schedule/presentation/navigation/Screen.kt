package ru.xaori.schedule.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Schedule : Screen("schedule")
    @Serializable
    data class ClientChoice(val showCancelButton: Boolean = true) : Screen("client_choice")
}