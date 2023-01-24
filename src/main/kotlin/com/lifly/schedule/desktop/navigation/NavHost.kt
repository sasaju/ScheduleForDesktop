package com.lifly.schedule.desktop.navigation

import LocalWindowsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.lifly.schedule.desktop.ui.import_timetable.ImportTimetableTab
import com.lifly.schedule.desktop.ui.more.MoreTab
import com.lifly.schedule.desktop.ui.show_timetable.ShowTimeTab


@Composable
fun HomeScreen() {
    val windowState = LocalWindowsState.current
    TabNavigator(ShowTimeTab) { router ->
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationRail {
                NavigationRailItem(
                    icon = { Icon(ShowTimeTab.options.icon!!, "课表") },
                    label = { Text(ShowTimeTab.options.title) },
                    selected = router.current == ShowTimeTab,
                    onClick = { router.current = ShowTimeTab }
                )
                NavigationRailItem(
                    icon = { Icon(ImportTimetableTab.options.icon!!, "导入") },
                    label = { Text(ImportTimetableTab.options.title) },
                    selected = router.current == ImportTimetableTab,
                    onClick = { router.current = ImportTimetableTab }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Search, "更多") },
                    label = { Text(MoreTab.options.title) },
                    selected = router.current == MoreTab,
                    onClick = {
                        router.current = MoreTab
                    }
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    TextButton(
                        onClick = {
                            windowState.size = DpSize(850.dp, 836.dp)
                            windowState.position = WindowPosition(Alignment.Center)
                        }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Refresh, null)
                            Text("重置窗口大小", textAlign = TextAlign.Center)
                        }
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()
            ) {
                CurrentTab()
            }
        }
    }
}

class NavHost {
}