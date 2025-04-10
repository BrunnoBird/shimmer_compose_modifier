package com.example.studytests.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Locale

/*** Shared Models and Styles ***/
data class CalendarStyle(
    val selectedDayColor: Color = Color.Green,
    val rangeDayColor: Color = Color.LightGray,
    val defaultTextStyle: TextStyle = TextStyle(fontSize = 14.sp),
    val selectedTextStyle: TextStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
)

fun Calendar.cloneAndSetDay(day: Int): Calendar {
    val newCal = this.clone() as Calendar
    newCal.set(Calendar.DAY_OF_MONTH, day)
    return newCal
}

fun Calendar.sameDay(other: Calendar?): Boolean {
    return other != null &&
            this.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            this.get(Calendar.MONTH) == other.get(Calendar.MONTH) &&
            this.get(Calendar.DAY_OF_MONTH) == other.get(Calendar.DAY_OF_MONTH)
}

fun Calendar.isBeforeDay(other: Calendar): Boolean =
    this.timeInMillis < other.timeInMillis && !sameDay(other)

fun Calendar.isAfterDay(other: Calendar): Boolean =
    this.timeInMillis > other.timeInMillis && !sameDay(other)

@Composable
fun CalendarSemMaterial(
    locale: Locale = Locale.getDefault(),
    style: CalendarStyle = CalendarStyle(),
    onDateRangeSelected: (Calendar?, Calendar?) -> List<String>
) {
    val currentMonth by remember {
        mutableStateOf(Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
        })
    }
    var startDate by remember { mutableStateOf<Calendar?>(null) }
    var endDate by remember { mutableStateOf<Calendar?>(null) }

    val days = remember(currentMonth.timeInMillis) {
        val daysList = mutableListOf<Calendar?>()
        val firstDayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK) - 1
        val maxDay = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

        repeat(firstDayOfWeek) {
            daysList.add(null)
        }

        for (day in 1..maxDay) {
            daysList.add(currentMonth.cloneAndSetDay(day))
        }

        daysList
    }

    val calendarDays = days.map { it }

    val selectedEvents = onDateRangeSelected(startDate, endDate)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            BasicText("<", modifier = Modifier.clickable {
                currentMonth.add(Calendar.MONTH, -1)
                currentMonth.set(Calendar.DAY_OF_MONTH, 1)
            })

            val monthDisplay = currentMonth.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
                ?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(locale) else it.toString()
                } ?: ""
            BasicText(monthDisplay, modifier = Modifier.align(Alignment.CenterVertically))

            BasicText(">", modifier = Modifier.clickable {
                currentMonth.add(Calendar.MONTH, 1)
                currentMonth.set(Calendar.DAY_OF_MONTH, 1)
            })
        }

        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val weekdays = (1..7).map { dayOfWeek ->
                Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_WEEK, dayOfWeek)
                }.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale) ?: ""
            }

            weekdays.forEach {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    BasicText(it, modifier = Modifier)
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            items(calendarDays.size) { index ->
                val day = calendarDays[index]
                val currentStartDate = startDate
                val currentEndDate = endDate
                var isInRange: Boolean = false

                val isSelected =
                    day?.sameDay(currentStartDate) == true || day?.sameDay(currentEndDate) == true

                if (currentStartDate != null && currentEndDate != null) {
                    isInRange =
                        day != null && day.isAfterDay(currentStartDate) && day.isBeforeDay(
                            currentEndDate
                        )

                }

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(2.dp)
                        .background(
                            when {
                                isSelected -> style.selectedDayColor
                                isInRange -> style.rangeDayColor
                                else -> Color.Transparent
                            }
                        )
                        .clickable(enabled = day != null) {
                            day?.let {
                                if (startDate == null || (startDate != null && endDate != null)) {
                                    startDate = it
                                    endDate = null
                                } else if (it.after(startDate)) {
                                    endDate = it
                                } else {
                                    startDate = it
                                    endDate = null
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = day?.get(Calendar.DAY_OF_MONTH)?.toString() ?: "",
                        style = if (isSelected) style.selectedTextStyle else style.defaultTextStyle
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        BasicText(
            "Eventos de ${startDate?.get(Calendar.DAY_OF_MONTH) ?: ""} até ${
                endDate?.get(
                    Calendar.DAY_OF_MONTH
                ) ?: ""
            }"
        )
        LazyColumn {
            items(selectedEvents) {
                BasicText("- $it", modifier = Modifier.padding(vertical = 2.dp))
            }
        }
    }
}
