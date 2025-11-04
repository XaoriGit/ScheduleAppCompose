package ru.xaori.schedule.domain.model.schedule

enum class WeekDays(
    val shortDay: String,
    val longDay: String
) {
    Monday("Пн", "Понедельник"),
    Tuesday("Вт", "Вторник"),
    Wednesday("Ср", "Среда"),
    Thursday("Чт", "Четверг"),
    Friday("Пт", "Пятница"),
    Saturday("Сб", "Суббота"),
    Sunday("Вс", "Воскресенье")
}