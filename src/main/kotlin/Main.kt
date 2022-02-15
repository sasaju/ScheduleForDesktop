// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.lifly.schedule.desktop.navigation.NavHostComponent
import com.lifly.schedule.desktop.theme.ScheduleTheme


@Composable
@Preview
fun App() {
    ScheduleTheme {
        remember {
            DefaultComponentContext(
                LifecycleRegistry()
            ).let(::NavHostComponent)
        }
            .render()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "课程表",
        icon = painterResource("drawable/ic_launcher-playstore.png")
    ) {
        App()
    }
}
