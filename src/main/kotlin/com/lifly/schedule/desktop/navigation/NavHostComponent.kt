package com.lifly.schedule.desktop.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfade
import com.arkivanov.decompose.router.*
import com.arkivanov.essenty.parcelable.Parcelable
import com.lifly.schedule.desktop.ui.import_timetable.ImportTimetableComponent
import com.lifly.schedule.desktop.ui.more.ShowMoreComponent
import com.lifly.schedule.desktop.ui.show_timetable.ShowTimetableComponent
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Navigator
 */
class NavHostComponent(
    componentContext: ComponentContext
) : Component, ComponentContext by componentContext {

    private val router = router<ScreenConfig, Component>(
        initialConfiguration = ScreenConfig.ShowTimetable,
        childFactory = ::createScreenComponent
    )

    private val index = MutableStateFlow(-1)
    /**
     * Factory function to create screen from given ScreenConfig
     */
    private fun createScreenComponent(
        screenConfig: ScreenConfig,
        componentContext: ComponentContext
    ): Component {
        return when (screenConfig) {
            is ScreenConfig.ShowTimetable -> ShowTimetableComponent(
                componentContext
            )
            is ScreenConfig.Greeting -> ShowMoreComponent(
                componentContext,
                screenConfig.name
            )
            is ScreenConfig.ImportTimetable -> ImportTimetableComponent(
                componentContext,
                ::onLoginSuccess
            )
            else -> ShowTimetableComponent(
                componentContext
            )
        }
    }


    /**
     * Invoked when `GO` button clicked (InputScreen)
     */
    private fun onLoginSuccess() {
        router.bringToFront(ScreenConfig.ShowTimetable)
        index.value = 0
    }

    /**
     * Invoked when `GO BACK` button clicked (GreetingScreen)
     */
    private fun onGoBackClicked() {
        router.pop()
    }


    /**
     * Renders screen as per request
     */
    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        val nowIndex = rememberSaveable  { mutableStateOf(0) }
        val nowIndex_ = index.collectAsState(-1)
        LaunchedEffect(nowIndex_.value){
            if (nowIndex_.value>=0){ nowIndex.value = nowIndex_.value }
        }
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationRail {
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Home, "课表") },
                    label = { Text("课表") },
                    selected = nowIndex.value==0,
                    onClick = {
                        nowIndex.value = 0
                        router.bringToFront(ScreenConfig.ShowTimetable)
                    }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.ArrowDropDown, "导入") },
                    label = { Text("导入") },
                    selected = nowIndex.value==1,
                    onClick = {
                        nowIndex.value = 1
                        router.bringToFront(ScreenConfig.ImportTimetable)
                    }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Search, "课表") },
                    label = { Text("更多") },
                    selected = nowIndex.value==2,
                    onClick = {
                        nowIndex.value = 2
                        router.bringToFront(ScreenConfig.Greeting("敬请期待~"))
                    }
                )

            }
            Children(routerState = router.state, animation = crossfade()) { that ->
                Box(modifier = Modifier.fillMaxSize()) {
                    that.instance.render()
                }
            }
        }
    }

    private sealed class ScreenConfig : Parcelable {
        data class Greeting(val name: String) : ScreenConfig()
        object ShowTimetable : ScreenConfig()
        object ImportTimetable: ScreenConfig()
    }
}

