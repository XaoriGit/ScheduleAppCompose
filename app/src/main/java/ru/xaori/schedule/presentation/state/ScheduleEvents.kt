package ru.xaori.schedule.presentation.state

sealed class ScheduleEvents {
    object GoToStart: ScheduleEvents()
    object Empty: ScheduleEvents()
}