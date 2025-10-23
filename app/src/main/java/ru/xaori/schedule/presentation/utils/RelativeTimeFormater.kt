package ru.xaori.schedule.presentation.utils

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object RelativeTimeFormatter {

    @OptIn(ExperimentalTime::class)
    fun format(lastUpdate: Instant, now: Instant = Clock.System.now()): String {
        val diff = now - lastUpdate
        val minutes = diff.inWholeMinutes
        val hours = diff.inWholeHours
        val days = diff.inWholeDays

        return when {
            minutes < 1 -> "только что"
            minutes < 60 -> "$minutes ${pluralMinutes(minutes)} назад"
            hours < 24 -> "$hours ${pluralHours(hours)} назад"
            days == 1L -> "вчера"
            days < 7 -> "$days ${pluralDays(days)} назад"
            days < 30 -> {
                val weeks = days / 7
                "$weeks ${pluralWeeks(weeks)} назад"
            }
            days < 365 -> {
                val months = days / 30
                "$months ${pluralMonths(months)} назад"
            }
            else -> {
                val years = days / 365
                "$years ${pluralYears(years)} назад"
            }
        }
    }

    private fun pluralMinutes(m: Long) = when {
        m % 10 == 1L && m % 100 != 11L -> "минута"
        m % 10 in 2..4 && (m % 100 !in 12..14) -> "минуты"
        else -> "минут"
    }

    private fun pluralHours(h: Long) = when {
        h % 10 == 1L && h % 100 != 11L -> "час"
        h % 10 in 2..4 && (h % 100 !in 12..14) -> "часа"
        else -> "часов"
    }

    private fun pluralDays(d: Long) = when {
        d % 10 == 1L && d % 100 != 11L -> "день"
        d % 10 in 2..4 && (d % 100 !in 12..14) -> "дня"
        else -> "дней"
    }

    private fun pluralWeeks(w: Long) = when {
        w % 10 == 1L && w % 100 != 11L -> "неделя"
        w % 10 in 2..4 && (w % 100 !in 12..14) -> "недели"
        else -> "недель"
    }

    private fun pluralMonths(m: Long) = when {
        m % 10 == 1L && m % 100 != 11L -> "месяц"
        m % 10 in 2..4 && (m % 100 !in 12..14) -> "месяца"
        else -> "месяцев"
    }

    private fun pluralYears(y: Long) = when {
        y % 10 == 1L && y % 100 != 11L -> "год"
        y % 10 in 2..4 && (y % 100 !in 12..14) -> "года"
        else -> "лет"
    }
}
