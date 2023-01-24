// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.lifly.schedule.desktop.navigation.HomeScreen
import com.lifly.schedule.desktop.theme.ScheduleTheme
import com.lifly.schedule.desktop.ui.dialog.MinOrCloseDialog
import java.awt.event.MouseEvent


fun main() {

    application {
        var isOpen by remember { mutableStateOf(true) }
        var mainVisible by remember { mutableStateOf(true) }
        val notification = rememberNotification("课程表已最小化至托盘", "课程表已最小化至托盘!")
        var close by remember { mutableStateOf(false) }
        val trayState = rememberTrayState()
        if (isOpen) {
            Tray(
                state = trayState,
                icon = painterResource("drawable/ic_launcher-playstore.png"),
                menu = {
                    Item(
                        "Open Main Page",
                        onClick = {
                            mainVisible = true
                        }
                    )
                    Item(
                        "Exit",
                        onClick = {
                            isOpen = false
                            exitApplication()
                        }
                    )
                },
                onAction = {
                    mainVisible = true
                }
            )
        }
        val windowState = LocalWindowsState.current
        LaunchedEffect(windowState.size){
            if (windowState.size.width.value<654F){
                windowState.size = DpSize(654.dp,windowState.size.height)
            }
        }
        Window(
            state = windowState,
            onCloseRequest = {
//                mainVisible = false
//                trayState.sendNotification(notification)
                close = true
            },
            title = "Schedule",
            visible = mainVisible,
            icon = painterResource("drawable/ic_launcher-playstore.png"),
            resizable = true,
            undecorated = false
        ) {
            ScheduleTheme {
                HomeScreen()
            }
//            LaunchedEffect(Unit){
//                delay(5)
//                val user32Lib : User32Lib = Native.load("user32", User32Lib::class.java)
//                val thisHWND = user32Lib.FindWindowA("SunAwtFrame", "Schedule")
//                val desktopHWND = user32Lib.FindWindowA("Progman", "Program Manager")
//                println(thisHWND.toString()+desktopHWND.toString())
//                if (desktopHWND != null) {
//                    if (thisHWND != null) {
//                        user32Lib.SetParent(thisHWND, desktopHWND)
//                    }
//                }
//            }
        }
        MinOrCloseDialog(
            close,
            min = {
                mainVisible = false
                trayState.sendNotification(notification)
                close=false
            },
            exit = {
                isOpen = false
                exitApplication()
            },
            close = {
                close = false
            }
        )
        val windowWidgetState = LocalWidgetStateNotitle.current
        val showWidget = LocalShowWidgetState.current
        var widgetVisible by remember { mutableStateOf(true) }
//        Window(
//            state =  windowWidgetState,
//            visible = widgetVisible,
//            onCloseRequest = {
//                widgetVisible = false
//            },
//            undecorated = true,
//            transparent = true,
//            title = "weekWidget"
//        ){
////            Box(
////                    modifier =
////                    Modifier
////                        .fillMaxWidth()
////                        .fillMaxHeight()
////                        .clip(RoundedCornerShape(10.dp))
////                        .background(Color.Red)
////                        .alpha(0.8f)
////                )
//            Surface(
//                modifier = Modifier.fillMaxSize().padding(5.dp).shadow(3.dp, RoundedCornerShape(20.dp)),
//                color = Color(55, 55, 55),
//                shape = RoundedCornerShape(20.dp) //window has round corners now
//            ) {
//                Text("Hello World!", color = Color.White)
//            }
//            LaunchedEffect(Unit){
//                delay(5)
//                val user32Lib : User32Lib = Native.load("user32", User32Lib::class.java)
//                val thisHWND = user32Lib.FindWindowA("SunAwtFrame", "weekWidget")
//                val desktopHWND = user32Lib.FindWindowA("Progman", "Program Manager")
//                println(thisHWND.toString()+desktopHWND.toString())
//                if (desktopHWND != null) {
//                    if (thisHWND != null) {
//                         user32Lib.SetParent(thisHWND, desktopHWND)
//                    }
//                }
//            }
//        }
    }


}

@Composable
fun Modifier.windowDraggable(): Modifier {
    val window = LocalWindowsState.current
    return pointerInput(Unit) {
        forEachGesture {
            awaitPointerEventScope {
                val firstEvent = awaitPointerEvent()
                val firstWindowPointer = firstEvent.mouseEvent?.point ?: return@awaitPointerEventScope

                while (true) {
                    val event = awaitPointerEvent()

                    val displayPointer = event.mouseEvent?.locationOnScreen ?: break

                    window.position = WindowPosition(displayPointer.x.dp, displayPointer.y.dp)

                    when (event.mouseEvent?.id) {
                        null,
                        MouseEvent.MOUSE_RELEASED -> {
                            break
                        }
                    }
                }
            }
        }
    }
}