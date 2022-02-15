package com.lifly.schedule.desktop.ui.show_timetable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.subscribe
import com.lifly.schedule.desktop.navigation.Component
import com.lifly.schedule.desktop.ui.Test
import java.util.logging.Logger

class ShowTimetableComponent(
    private val componentContext: ComponentContext,
): Component, ComponentContext by componentContext {
    private val logger: Logger = Logger.getLogger("test")
    init {
        logger.warning("initShowTimetable")
        lifecycle.subscribe(
            onResume = { println("onResume") }
        )
    }
    @Composable
    override fun render() {
        Test()
    }
}