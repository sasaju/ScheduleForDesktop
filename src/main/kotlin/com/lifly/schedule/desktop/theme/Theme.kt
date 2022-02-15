package com.lifly.schedule.desktop.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ScheduleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        content()
    }
}