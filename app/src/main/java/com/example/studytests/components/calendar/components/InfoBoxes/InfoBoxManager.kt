package com.example.studytests.components.calendar.components.InfoBoxes

import androidx.compose.runtime.Composable
import com.example.studytests.components.calendar.data.DayInfoData
import com.example.studytests.components.calendar.utils.sameDay
import java.util.Calendar
import java.util.Locale

@Composable
fun InfoBoxManager(
    daysInfo: List<DayInfoData>,
    selectedDate: Calendar,
    locale: Locale,
    iconInfoBox: @Composable (() -> Unit)? = null
) {
    val infoDataSelectedDate = daysInfo.find { it.date.sameDay(selectedDate) }

    if (daysInfo.isNotEmpty() && infoDataSelectedDate != null) {
        InfoBoxDate(infoSelectedDate = infoDataSelectedDate, icon = iconInfoBox)
    }

    if (daysInfo.isEmpty()) {
        InfoBoxGeneric(selectedDate = selectedDate, locale = locale)
    }
}