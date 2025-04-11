package com.example.studytests.components.calendar.components.headers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytests.components.calendar.components.CalendarDropDown
import com.example.studytests.components.calendar.data.CalendarItemData

@Composable
internal fun CalendarHeaderDropdown(
    selectedMonth: CalendarItemData?,
    months: List<CalendarItemData>,
    selectedYear: CalendarItemData?,
    years: List<CalendarItemData>,
    onMonthSelected: (CalendarItemData) -> Unit,
    onYearSelected: (CalendarItemData) -> Unit
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