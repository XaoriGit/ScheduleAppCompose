package ru.xaori.schedule.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.xaori.schedule.domain.model.WeekDays

@Serializable
data class ScheduleDataResponse(
    @SerialName("client_name") val clientName: String,
    @SerialName("last_update") val lastUpdate: String,
    val schedules: List<ScheduleDay>
)

@Serializable
data class ScheduleDay(
    val date: String,
    @SerialName("week_day") val weekDay: Int,
    val lessons: List<Lesson>
) {
    val localDate: LocalDate
        get() = LocalDate.parse(date)

    val dayOfMonth: Int
        get() = localDate.day

    val weekDayEnum: WeekDays
        get() = WeekDays.entries[weekDay]
}

@Serializable
data class Lesson(
    val number: Int,
    val time: String,
    val items: List<ScheduleItem>
)

@Serializable
data class ScheduleItem(
    val title: String,
    val type: String,
    val partner: String,
    val location: String
)