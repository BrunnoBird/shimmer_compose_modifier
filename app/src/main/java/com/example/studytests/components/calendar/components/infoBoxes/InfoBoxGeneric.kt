package com.example.studytests.components.calendar.components.infoBoxes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.studytests.R
import java.util.Calendar
import java.util.Locale

@Composable
fun InfoBoxGeneric(
    modifier: Modifier = Modifier,
    selectedDate: Calendar,
    locale: Locale = Locale.getDefault(),
) {
    Box(
        modifier = modifier
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
            Column {
                BasicText(
                    text = "Data selecionada",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                BasicText(
                    text = formatFullDate(selectedDate, locale)
                )
            }
        }
    }
}

@Composable
private fun formatFullDate(calendar: Calendar, locale: Locale = Locale.getDefault()): String {
    val context = LocalContext.current
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month =
        calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)?.lowercase(locale).orEmpty()
    val year = calendar.get(Calendar.YEAR)

    return context.getString(R.string.info_box_date_full_date_format, day, month, year)
}
