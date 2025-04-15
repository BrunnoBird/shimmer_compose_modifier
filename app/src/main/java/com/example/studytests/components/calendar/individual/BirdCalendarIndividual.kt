package com.example.studytests.components.calendar.individual

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.studytests.components.calendar.components.calendars.CalendarSingleDate
import com.example.studytests.components.calendar.components.headers.CalendarHeaderDropdown
import com.example.studytests.components.calendar.components.infoBoxes.InfoBoxManager
import com.example.studytests.components.calendar.data.CalendarItemData
import com.example.studytests.components.calendar.data.DayInfoData
import com.example.studytests.components.calendar.utils.getDaysFromMonth
import com.example.studytests.components.calendar.utils.getMonths
import com.example.studytests.components.calendar.utils.getYears
import java.util.Calendar
import java.util.Locale

@Composable
fun BirdCalendarIndividual(
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault(),
    daysInfo: List<DayInfoData> = emptyList(),
    hasInfoBox: Boolean = true,
    iconInfoBox: @Composable (() -> Unit)? = null,
    onDateSelected: ((Calendar) -> Unit)? = null
) {
    val today = remember(locale) { Calendar.getInstance(locale) }
    val years = getYears(locale)
    val months = getMonths(locale)

    var selectedMonth by remember {
        mutableStateOf(
            months.firstOrNull { monthItem ->
                val cal = monthItem.value as Calendar
                cal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
            }
        )
    }

    var selectedYear by remember {
        mutableStateOf(
            years.firstOrNull { yearItem ->
                val cal = yearItem.value as Calendar
                cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)
            }
        )
    }

    var selectedDate by remember { mutableStateOf(today.clone() as Calendar) }

    val days = selectedMonth?.let { monthItem ->
        val monthCalendar = monthItem.value as Calendar
        val selectedYearValue = (selectedYear?.value as? Calendar)?.get(Calendar.YEAR)

        selectedYearValue?.let {
            monthCalendar.set(Calendar.YEAR, it)
        }
        getDaysFromMonth(CalendarItemData(monthCalendar, monthItem.displayValue), locale)
    }.orEmpty()

    Column(modifier = modifier) {
        CalendarHeaderDropdown(
            selectedMonth = selectedMonth,
            months = months,
            selectedYear = selectedYear,
            years = years,
            onMonthSelected = { selectedMonth = it },
            onYearSelected = { selectedYear = it }
        )

        CalendarSingleDate(days, today, selectedDate, locale) {
            selectedDate = it
            onDateSelected?.invoke(it)
        }

        if (hasInfoBox) {
            InfoBoxManager(daysInfo, selectedDate, locale, iconInfoBox)
        }
    }
}
