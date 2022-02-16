package com.lifly.schedule.desktop.ui.import_timetable

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.util.Convert
import com.lifly.schedule.desktop.logic.util.appGlobalScope
import com.lifly.schedule.desktop.navigation.Component
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow


class ImportTimetableComponent(
    private val componentContext: ComponentContext,
    private val onSuccess:()->Unit,
): Component, ComponentContext by componentContext  {

    private val userId = MutableStateFlow("")
    init {
        appGlobalScope.launch {
            userId.value = Repository.getId2().id
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun render() {
        val id = userId.collectAsState("")
        val state = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = state,
            content = {
                Login(id.value,"登录"){ courseResponse ->
                    if (courseResponse.status=="yes" || courseResponse.status=="no"){
                        scope.launch {
                            val a = Repository.replaceCourseBeans(Convert.courseResponseToBean(courseResponse.allCourse))
                            state.snackbarHostState.showSnackbar("登录并保存成功")
                            onSuccess()
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