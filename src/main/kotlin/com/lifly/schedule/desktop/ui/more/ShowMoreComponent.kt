package com.lifly.schedule.desktop.ui.more

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.lifly.schedule.desktop.navigation.Component
import com.lifly.schedule.desktop.ui.Test

class ShowMoreComponent(
    private val componentContext: ComponentContext,
    private val text:String,
): Component, ComponentContext by componentContext {
    @Composable
    override fun render() {
        ShowMore(text)
    }
}