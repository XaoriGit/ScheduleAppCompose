package ru.xaori.schedule.presentation.state

sealed class AppBarStatus {
    data class SubTitle(val subTitle: String): AppBarStatus()
    data class SubTitleError(val subTitle: String): AppBarStatus()
    object Loading: AppBarStatus()
}