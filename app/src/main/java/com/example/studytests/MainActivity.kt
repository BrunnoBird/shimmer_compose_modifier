package com.example.studytests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studytests.components.calendar.data.DayInfoData
import com.example.studytests.components.calendar.individual.BirdCalendarIndividual
import java.util.Calendar
import java.util.Locale


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
                        val calendarListInfo = listOf(
                            DayInfoData(
                                date = Calendar.getInstance()
                                    .apply { set(2025, Calendar.NOVEMBER, 5) },
                                message = "Dia do evento: apresentação do projeto"
                            ),
                            DayInfoData(
                                date = Calendar.getInstance()
                                    .apply { set(2025, Calendar.NOVEMBER, 7) },
                                message = "Lembrete: Enviar documentação final"
                            ),
                            DayInfoData(
                                date = Calendar.getInstance()
                                    .apply { set(2025, Calendar.APRIL, 12) },
                                message = "Lembrete: Bora codar o calendar?"
                            )
                        )

                        BirdCalendarIndividual(
                            modifier = Modifier.padding(16.dp),
                            locale = Locale("en", "US"),
//                            daysInfo = calendarListInfo,
//                            iconInfoBox = {
//                                Icon(
//                                    imageVector = Icons.Filled.Create,
//                                    contentDescription = null
//                                )
//                            }
                        )
                    }
                }
            }
        }
    }
}