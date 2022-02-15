package com.lifly.schedule.desktop.ui.import_timetable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.essenty.statekeeper.consume
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.util.appGlobalScope
import com.lifly.schedule.desktop.navigation.Component
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext


class ImportTimetableComponent(
    private val componentContext: ComponentContext,
): Component, ComponentContext by componentContext  {
    private var state =  stateKeeper.consume("userId")?:UserId()
    val a = MutableStateFlow<String>("")
    init {
        stateKeeper.register(key = "userId"){ state }
        appGlobalScope.launch {
            a.value = Repository.getId2()
        }
        println("importInit")
    }
    @Composable
    override fun render() {
        val a = a.collectAsState("")
        Login(a.value)
    }

    @Parcelize
    data class UserId(
        val id: String = ""
    ):Parcelable
}