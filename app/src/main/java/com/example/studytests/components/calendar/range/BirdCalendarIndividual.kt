package com.example.studytests.components.calendar.range

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.studytests.components.calendar.components.CalendarDropDown
import com.example.studytests.components.calendar.data.CalendarItem
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

@Composable
fun BirdCalendarDouble(
    locale: Locale = Locale.getDefault()
) {
    var selectedMonth by remember { mutableStateOf<CalendarItem?>(null) }
    var selectedYear by remember { mutableStateOf<CalendarItem?>(null) }
    var startDate by remember { mutableStateOf<Calendar?>(null) }
    var endDate by remember { mutableStateOf<Calendar?>(null) }

    val years = getYears(locale)
    val months = getMonths(locale)
    val days = selectedMonth?.let { getDaysFromMonth(it, locale) }.orEmpty()

    Header(
        selectedMonth = selectedMonth,
        months = months,
        selectedYear = selectedYear,
        years = years,
        onMonthSelected = { selectedMonth = it },
        onYearSelected = { selectedYear = it }
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(days.size) { index ->
            val day = days[index].value as Calendar

            val isSelected = startDate?.sameDay(day) == true || endDate?.sameDay(day) == true
            val isInRange =
                startDate != null && endDate != null && day.after(startDate) && day.before(endDate)

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(2.dp)
                    .background(
                        when {
                            isSelected -> Color(0xFF1976D2)
                            isInRange -> Color(0x332196F3)
                            else -> Color.Transparent
                        }
                    )
                    .clickable {
                        if (startDate == null || (startDate != null && endDate != null)) {
                            startDate = day
                            endDate = null
                        } else if (day.after(startDate)) {
                            endDate = day
                        } else {
                            startDate = day
                            endDate = null
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = day.get(Calendar.DAY_OF_MONTH).toString(),
                    style = if (isSelected) TextStyle(color = Color.White) else TextStyle(color = Color.Black)
                )
            }
        }
    }
}

@Composable
private fun Header(
    selectedMonth: CalendarItem?,
    months: List<CalendarItem>,
    selectedYear: CalendarItem?,
    years: List<CalendarItem>,
    onMonthSelected: (CalendarItem) -> Unit,
    onYearSelected: (CalendarItem) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CalendarDropDown(
            placeholder = "Mês",
            modifier = Modifier.padding(start = 16.dp),
            selectedItem = selectedMonth,
            items = months,
            onItemSelected = onMonthSelected
        )
        CalendarDropDown(
            placeholder = "Ano",
            modifier = Modifier.padding(end = 16.dp),
            selectedItem = selectedYear,
            items = years,
            onItemSelected = onYearSelected
        )
    }
}

private fun getYears(locale: Locale = Locale.getDefault()): List<CalendarItem> {
    val currentYear = Calendar.getInstance(locale).get(Calendar.YEAR)
    val yearRange = (currentYear - 50)..(currentYear + 10)
    return yearRange.map { year ->
        val calendar = GregorianCalendar(year, 0, 1)
        CalendarItem(calendar, year.toString())
    }
}

private fun getMonths(locale: Locale): List<CalendarItem> {
    return Calendar.getInstance(locale).let { calendar ->
        (0 until calendar.getMaximum(Calendar.MONTH) + 1).map { monthIndex ->
            calendar.set(Calendar.MONTH, monthIndex)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) ?: ""
            CalendarItem(
                calendar.clone() as Calendar,
                monthName.replaceFirstChar { it.titlecase(locale) })
        }
    }
}

private fun getDaysFromMonth(
    monthItem: CalendarItem,
    locale: Locale = Locale.getDefault()
): List<CalendarItem> {
    val calendar = monthItem.value as? Calendar ?: return emptyList()
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val calendarCopy = calendar.clone() as Calendar
    return (1..daysInMonth).map { day ->
        calendarCopy.set(Calendar.DAY_OF_MONTH, day)
        val display = day.toString()
        CalendarItem(calendarCopy.clone() as Calendar, display)
    }
}

private fun Calendar.sameDay(other: Calendar?): Boolean {
    if (other == null) return false
    return get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            get(Calendar.MONTH) == other.get(Calendar.MONTH) &&
            get(Calendar.DAY_OF_MONTH) == other.get(Calendar.DAY_OF_MONTH)
}
