package com.lifly.schedule.desktop.ui.show_timetable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.lifly.schedule.desktop.logic.Repository.logger
import com.lifly.schedule.desktop.logic.dao.SettingsSerializer
import com.lifly.schedule.desktop.ui.ShowCourseAndNowWeek

object ShowTimeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "课表"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LaunchedEffect(Unit){
            val settings=SettingsSerializer.appSettings
            logger.info { settings.userVersion.toString() }
        }
        ShowCourseAndNowWeek()
    }
}
