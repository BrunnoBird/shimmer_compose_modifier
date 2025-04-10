package com.example.studytests.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytests.components.calendar.components.CalendarDropDown
import com.example.studytests.components.calendar.components.CalendarItem
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

@Composable
fun BirdCalendarIndividual(
    locale: Locale = Locale.getDefault()
) {
    var selectedMonth by remember { mutableStateOf<CalendarItem?>(null) }
    var selectedYear by remember { mutableStateOf<CalendarItem?>(null) }

    val months = Calendar.getInstance(locale).let { calendar ->
        (0 until calendar.getMaximum(Calendar.MONTH) + 1).map { monthIndex ->
            calendar.set(Calendar.MONTH, monthIndex)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) ?: ""
            CalendarItem(
                calendar.clone() as Calendar,
                monthName.replaceFirstChar { it.titlecase(locale) })
        }
    }

    val currentYear = Calendar.getInstance(locale).get(Calendar.YEAR)
    val yearRange = (currentYear - 50)..(currentYear + 10)
    val years = yearRange.map { year ->
        val calendar = GregorianCalendar(year, 0, 1)
        CalendarItem(calendar, year.toString())
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CalendarDropDown(
            modifier = Modifier.padding(start = 16.dp),
            selectedItem = selectedMonth,
            items = months,
            onItemSelected = { item ->
                selectedMonth = item
            }
        )
        CalendarDropDown(
            selectedItem = selectedYear,
            items = years,
            onItemSelected = { item ->
                selectedYear = item
            }
        )
    }
}
