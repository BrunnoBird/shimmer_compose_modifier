package com.example.studytests.components.calendar.components.calendars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Locale

@Composable
internal fun CalendarDayInitialsHeader(
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault()
) {
    val daysOfWeek = remember(locale) {
        val symbols = java.text.DateFormatSymbols(locale)
        val weekdays = symbols.shortWeekdays
        val firstDayOfWeek = Calendar.getInstance(locale).firstDayOfWeek

        (0 until 7).map { i ->
            val dayIndex = (firstDayOfWeek + i - 1) % 7 + 1
            weekdays[dayIndex].first().uppercaseChar().toString()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        daysOfWeek.forEach { letter ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = letter,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }
    }
}
