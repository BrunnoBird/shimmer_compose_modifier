package com.example.studytests.components.calendar.utils

import com.example.studytests.components.calendar.data.CalendarItemData
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

internal fun getYears(
    locale: Locale = Locale.getDefault(),
    pastYearsCount: Int = 5,
    futureYearsCount: Int = 10
): List<CalendarItemData> {
    val currentYear = Calendar.getInstance(locale).get(Calendar.YEAR)
    val range = (currentYear - pastYearsCount)..(currentYear + futureYearsCount)

    return range.map { year ->
        val calendar = GregorianCalendar(year, Calendar.JANUARY, 1)
        CalendarItemData(calendar, year.toString())
    }
}

internal fun getMonths(locale: Locale): List<CalendarItemData> {
    return (0..11).map { monthIndex ->
        val calendar = Calendar.getInstance(locale).apply {
            set(Calendar.MONTH, monthIndex)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale).orEmpty()
        val display = monthName.replaceFirstChar { it.titlecase(locale) }

        CalendarItemData(calendar, display)
    }
}


internal fun getDaysFromMonth(
    monthItem: CalendarItemData,
    locale: Locale = Locale.getDefault()
): List<CalendarItemData> {
    val originalCalendar = monthItem.value as? Calendar ?: return emptyList()
    val calendar = originalCalendar.clone() as Calendar
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    // Definir o primeiro dia do mês
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    // Descobrir em qual dia da semana o mês começa (1 = domingo, 7 = sábado)
    val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)

    // Qual é o primeiro dia da semana no locale (1 = domingo no Brasil)
    val firstDayOfWeek = Calendar.getInstance(locale).firstDayOfWeek

    // Quantos espaços em branco antes do dia 1
    val startOffset = (7 + firstDayOfMonth - firstDayOfWeek) % 7

    // Gerar dias em branco
    val blankDays = List(startOffset) {
        CalendarItemData(Calendar.getInstance().apply { set(0, 0, 0) }, "")
    }

    // Gerar os dias do mês
    val days = (1..daysInMonth).map { day ->
        calendar.set(Calendar.DAY_OF_MONTH, day)
        CalendarItemData(calendar.clone() as Calendar, day.toString())
    }

    return blankDays + days
}

internal fun Calendar.sameDay(other: Calendar?): Boolean {
    if (other == null) return false
    return get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            get(Calendar.MONTH) == other.get(Calendar.MONTH) &&
            get(Calendar.DAY_OF_MONTH) == other.get(Calendar.DAY_OF_MONTH)
}