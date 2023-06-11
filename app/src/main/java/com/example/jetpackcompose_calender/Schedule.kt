package com.example.jetpackcompose_calender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

data class Schedule(val date: LocalDate, var event: String)

object ScheduleController {
    private val schedules = mutableMapOf<LocalDate, Schedule>()

    fun getSchedule(date: LocalDate): Schedule {
        return schedules.getOrPut(date) { Schedule(date, "なし") }
    }

    fun addSchedule(schedule: Schedule) {
        schedules[schedule.date] = schedule
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDialog(showDialog: MutableState<Boolean>, date: LocalDate) {
    val schedule = ScheduleController.getSchedule(date)
    val newEvent = remember { mutableStateOf(schedule.event) }

    AlertDialog(
        onDismissRequest = {
            showDialog.value = false
        },
        title = { Text(text = "予定の詳細") },
        text = {
            Column {
                Text(text = "日付: $date")
                Text(text = "予定: ${schedule.event}")
                TextField(value = newEvent.value, onValueChange = { newEvent.value = it }, label = { Text("新しい予定") })
            }
        },
        confirmButton = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        schedule.event = newEvent.value
                        ScheduleController.addSchedule(schedule)
                        showDialog.value = false
                    }
                ) {
                    Text("予定を追加")
                }
            }
        }
    )
}
