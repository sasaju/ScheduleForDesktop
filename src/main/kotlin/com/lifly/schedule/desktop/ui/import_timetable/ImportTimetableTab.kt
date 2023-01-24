package com.lifly.schedule.desktop.ui.import_timetable

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.util.Convert
import com.lifly.schedule.desktop.logic.util.appGlobalScope
import com.lifly.schedule.desktop.ui.show_timetable.ShowTimeTab
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object ImportTimetableTab : Tab {
    private val userId = MutableStateFlow("t")
    init {
//        GlobalScope.launch {
//            userId.value = Repository.getId2().id
//        }

    }
    override val options: TabOptions
        @Composable
        get() {
            val title = "导入"
            val icon = rememberVectorPainter(Icons.Default.ArrowDropDown)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val id = userId.collectAsState("")
//        val id = Repository.getId().collectAsState("")
        val state = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = state,
            content = {
                Login(id.value,"登录"){ courseResponse ->
                    if (courseResponse.status=="yes" || courseResponse.status=="no"){
                        scope.launch {
                            Repository.deleteAllCourseBean()
                            val a = Repository.replaceCourseBeans(Convert.courseResponseToBean(courseResponse.allCourse))
                            tabNavigator.current = ShowTimeTab
                        }
                    } else {
                        scope.launch {
                            state.snackbarHostState.showSnackbar(courseResponse.status)
                        }
                    }
                }
            }
        )
    }
}
