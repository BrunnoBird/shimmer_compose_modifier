package com.example.studytests.components.calendar.individual

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import com.example.studytests.components.calendar.components.CalendarDropDown
import com.example.studytests.components.calendar.data.CalendarItem
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

@Composable
fun BirdCalendarIndividual(
    locale: Locale = Locale.getDefault()
) {
    var selectedMonth by remember { mutableStateOf<CalendarItem?>(null) }
    var selectedYear by remember { mutableStateOf<CalendarItem?>(null) }
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }

    val today = remember(locale) { Calendar.getInstance(locale) }
    val years = getYears(locale)
    val months = getMonths(locale)
    val days = selectedMonth?.let { getDaysFromMonth(it) }.orEmpty()

    Column {
        Header(
            selectedMonth = selectedMonth,
            months = months,
            selectedYear = selectedYear,
            years = years,
            onMonthSelected = { selectedMonth = it },
            onYearSelected = { selectedYear = it }
        )

        CalendarItem(days, today, selectedDate)

        selectedMonth?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    BasicText(text = "Texto informativo, alerta ou dica sobre a data escolhida.")
                }
            }
        }
    }
}

@Composable
private fun CalendarItem(
    days: List<CalendarItem>,
    today: Calendar,
    selectedDate: Calendar?
) {
    var selectedDate1 = selectedDate
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(days.size) { index ->
            val day = days[index].value as Calendar
            val isToday = today.sameDay(day)
            val isSelected = selectedDate1?.sameDay(day) == true

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(2.dp)
                    .then(
                        if (isSelected) Modifier
                            .background(Color.Black, CircleShape)
                        else if (isToday) Modifier
                            .border(2.dp, Color.Black, CircleShape)
                        else Modifier
                    )
                    .clickable {
                        selectedDate1 = day
                    },
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = day.get(Calendar.DAY_OF_MONTH).toString(),
                    style = TextStyle(
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 14.sp
                    )
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

