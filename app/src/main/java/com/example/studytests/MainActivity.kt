package com.example.studytests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.studytests.components.calendar.BirdCalendarIndividual
import com.example.studytests.components.calendar.CalendarSemMaterial
import com.example.studytests.components.calendar.CalendarStyle
import java.util.Calendar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
            ) { paddingValues ->
                MaterialTheme {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                    ) {
                        BirdCalendarIndividual()
                    }
                }
            }
        }
    }
}

@Composable
fun EventBookingCalendar() {
    // Sample event data (replace with your data source)
    data class Event(val name: String, val startDate: Calendar, val endDate: Calendar)

    val events = listOf(
        Event(
            "Meeting 1",
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 10) },
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 10) }),
        Event(
            "Dentist",
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 12) },
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 13) }),
        Event(
            "Meeting 2",
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 17) },
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 19) }),
        Event(
            "Trip",
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 23) },
            Calendar.getInstance().apply { set(2024, Calendar.JUNE, 27) })
    )

    //Function to filter the events, if it is contained in the selected range
    fun filterEventsInRange(
        events: List<Event>,
        startDate: Calendar?,
        endDate: Calendar?
    ): List<String> {
        if (startDate == null || endDate == null) {
            return emptyList()
        }

        return events.filter { event ->
            event.startDate.timeInMillis >= startDate.timeInMillis && event.endDate.timeInMillis <= endDate.timeInMillis
        }.map { it.name }
    }

    CalendarSemMaterial(
        onDateRangeSelected = { start, end ->
            filterEventsInRange(events, start, end)
        }
    )
}

@Composable
fun TravelBookingCalendar() {
    // Sample unavailable days data (replace with your data source)
    val unavailableDays = listOf(
        Calendar.getInstance().apply { set(2024, Calendar.JUNE, 11) },
        Calendar.getInstance().apply { set(2024, Calendar.JUNE, 12) },
        Calendar.getInstance().apply { set(2024, Calendar.JUNE, 20) }
    )

    val customStyle = CalendarStyle(
        selectedDayColor = Color.Blue,
        rangeDayColor = Color.Cyan,
        defaultTextStyle = TextStyle(fontSize = 14.sp),
        selectedTextStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
    )

    fun filterAvailability(
        unavailableDays: List<Calendar>,
        startDate: Calendar?,
        endDate: Calendar?
    ): List<String> {
        if (startDate == null || endDate == null) {
            return emptyList()
        }

        val stringList: MutableList<String> = mutableListOf()
        unavailableDays.forEach {
            if (it.timeInMillis >= startDate.timeInMillis && it.timeInMillis <= endDate.timeInMillis) {
                stringList.add("No availability on $it")
            }
        }

        return stringList
    }

    CalendarSemMaterial(
        style = customStyle,
        onDateRangeSelected = { start, end ->
            filterAvailability(unavailableDays, start, end)
        }
    )
}

@Composable
fun TaskManagementCalendar() {
    // Sample task data
    data class Task(val name: String, val deadline: Calendar)

    val tasks = listOf(
        Task("Finish project", Calendar.getInstance().apply { set(2024, Calendar.JUNE, 15) }),
        Task("Meeting", Calendar.getInstance().apply { set(2024, Calendar.JUNE, 20) }),
        Task("Prepare presentation", Calendar.getInstance().apply { set(2024, Calendar.JUNE, 28) })
    )

    fun filterTaskInRange(
        tasks: List<Task>,
        startDate: Calendar?,
        endDate: Calendar?
    ): List<String> {
        if (startDate == null || endDate == null) {
            return emptyList()
        }
        return tasks.filter { task ->
            task.deadline.timeInMillis >= startDate.timeInMillis && task.deadline.timeInMillis <= endDate.timeInMillis
        }.map { it.name }
    }

    CalendarSemMaterial(
        onDateRangeSelected = { start, end ->
            filterTaskInRange(tasks, start, end)
        }
    )
}