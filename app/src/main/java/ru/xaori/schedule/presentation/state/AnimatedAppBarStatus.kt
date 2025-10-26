package ru.xaori.schedule.presentation.state

sealed class AnimatedAppBarStatus {
    data class SubTitle(val subTitle: String): AnimatedAppBarStatus()
    data class SubTitleError(val subTitle: String): AnimatedAppBarStatus()
    object Loading: AnimatedAppBarStatus()
}