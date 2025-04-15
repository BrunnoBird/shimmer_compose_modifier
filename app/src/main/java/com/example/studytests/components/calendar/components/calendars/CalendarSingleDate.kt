package com.example.studytests.components.calendar.components.calendars

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studytests.components.calendar.data.CalendarItemData
import com.example.studytests.components.calendar.utils.sameDay
import java.util.Calendar
import java.util.Locale

@Composable
internal fun CalendarSingleDate(
    days: List<CalendarItemData>,
    today: Calendar,
    selectedDate: Calendar?,
    locale: Locale,
    onDateSelected: (Calendar) -> Unit
) {
    Column {
        CalendarDayInitialsHeader(modifier = Modifier.padding(top = 8.dp), locale = locale)

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(days.size) { index ->
                val item = days[index]
                val calendar = item.value
                val isBlank = item.displayValue.isBlank()
                val day = if (!isBlank) calendar as? Calendar else null
                val isToday = today.sameDay(day)
                val isSelected = selectedDate?.sameDay(day) == true

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
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                            onClick = { onDateSelected(day ?: Calendar.getInstance()) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = (day?.get(Calendar.DAY_OF_MONTH) ?: "").toString(),
                        style = TextStyle(
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}
