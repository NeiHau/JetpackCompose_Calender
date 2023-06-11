package com.example.jetpackcompose_calender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.jetpackcompose_calender.ui.theme.JetpackCompose_CalenderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackCompose_CalenderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendarView(2023, 6)
                }

            }
        }
    }
}

