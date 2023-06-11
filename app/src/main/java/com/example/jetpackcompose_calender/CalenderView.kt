// CalendarView.kt
package com.example.jetpackcompose_calender

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Preview(showBackground = true)
@Composable
fun CalendarViewPreview() {
    CalendarView(2023, 6)
}

// カレンダー本体のUI
@Composable
fun CalendarView(year: Int, month: Int) {
    var dayOfMonth = 1
    val currentDay = LocalDate.now().dayOfMonth
    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(year, month, 2)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek

    val locale = Locale.getDefault()

    val showDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // 曜日を表示
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            DayOfWeek.values().toList().let { it.subList(6, 7) + it.subList(0, 6) }
                .forEach { dayOfWeek ->
                    when (dayOfWeek) {
                        DayOfWeek.SUNDAY -> {
                            Text(
                                color = Color.Red,
                                text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
                                modifier = Modifier.weight(1f).padding(4.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        DayOfWeek.SATURDAY -> {
                            Text(
                                color = Color.Blue,
                                text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
                                modifier = Modifier.weight(1f).padding(4.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        else -> {
                            Text(
                                text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale),
                                modifier = Modifier.weight(1f).padding(4.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
        }

        // 日にちを表示
        while (dayOfMonth <= daysInMonth) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DayOfWeek.values().forEach { dayOfWeek ->
                    if (dayOfMonth == 1 && dayOfWeek != firstDayOfWeek) {
                        Text(text = "", modifier = Modifier.weight(1f).padding(4.dp))
                    } else if (dayOfMonth <= daysInMonth) {
                        val date = LocalDate.of(year, month, dayOfMonth)
                        val dayText = dayOfMonth.toString()
                        Text(
                            text = dayText,
                            color = if (dayOfMonth == currentDay) Color.Red else Color.Black,
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .background(if (dayOfMonth == currentDay) Color.LightGray else Color.Transparent)
                                .clickable {
                                    selectedDate.value = date
                                    showDialog.value = true
                                },
                            textAlign = TextAlign.Center
                        )
                        dayOfMonth++
                    } else {
                        Text(text = "", modifier = Modifier.weight(1f).padding(4.dp))
                    }
                }
            }
        }
    }

    if (showDialog.value) {
        ScheduleDialog(showDialog, selectedDate.value)
    }
}