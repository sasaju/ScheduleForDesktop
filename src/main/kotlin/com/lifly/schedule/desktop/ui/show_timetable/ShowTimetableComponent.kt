package com.lifly.schedule.desktop.ui.show_timetable

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.subscribe
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.dao.SettingsSerializer
import com.lifly.schedule.desktop.logic.model.AppSettings
import com.lifly.schedule.desktop.navigation.Component
import com.lifly.schedule.desktop.ui.ShowCourse
import java.util.logging.Logger

class ShowTimetableComponent(
    private val componentContext: ComponentContext,
) : Component, ComponentContext by componentContext {
    private val logger: Logger = Repository.logger
    lateinit var settings:AppSettings

    init {
        println("ShowTimetableComponent实例化一次")
        lifecycle.subscribe(
            onCreate = {
                logger.info{"onCreate"}
                settings=SettingsSerializer.appSettings
//                logger.info { Repository.loadAllCourseToOneByOne().toString() }
            },
            onResume = { logger.info{"onResume"} }
        )
    }

    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit){
            logger.info { settings.userVersion.toString() }
        }
        ShowCourse()
    }
}