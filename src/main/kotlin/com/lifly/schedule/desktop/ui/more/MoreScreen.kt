//package com.lifly.schedule.desktop.ui.more
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.TextButton
//import androidx.compose.material.TextField
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.awt.ComposeWindow
//import androidx.compose.ui.awt.SwingPanel
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.key.*
//import androidx.compose.ui.layout.Layout
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.round
//import androidx.compose.ui.window.Window
//import androidx.compose.ui.window.WindowPlacement
//import androidx.compose.ui.window.WindowState
//import com.lifly.schedule.desktop.logic.Repository
//import com.lifly.schedule.desktop.logic.util.pager.ExperimentalPagerApi
//import com.lifly.schedule.desktop.logic.util.pager.HorizontalPager
//import com.lifly.schedule.desktop.logic.util.pager.rememberPagerState
//import com.sun.javafx.application.PlatformImpl
//import javafx.application.Platform
//import javafx.concurrent.Worker
//import javafx.embed.swing.JFXPanel
//import javafx.scene.Scene
//import javafx.scene.web.WebEngine
//import javafx.scene.web.WebView
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.launch
//import netscape.javascript.JSObject
//import org.jetbrains.skia.impl.Log
//import java.awt.BorderLayout
//import javax.swing.JPanel
//
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun ShowMore(text: String) {
//    val pagerState = rememberPagerState(initialPage = 2)
//    val scope = rememberCoroutineScope()
//    var showWebView by rememberSaveable { mutableStateOf(false) }
//    HorizontalPager(
//        state = pagerState,
//        count = 1,
//        modifier = Modifier.fillMaxSize()
//    ) { page ->
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
////            Text("$page", modifier = Modifier.clickable {
////                scope.launch {
////                    pagerState.animateScrollToPage(page+1)
////                }
////            })
//            Text(
//                text = text,
//                modifier = Modifier
//                    .clickable {
//                        showWebView = true
//                    }
//            )
//        }
//        if (showWebView) {
//            WebViewWindow { showWebView = false }
//        }
////        if(showWebView){
////            Dialog(
////                visible = true,
////                content = {
////                    Box(){
////
////                    }
////                },
////                onCloseRequest = {
////                    showWebView = false
////                }
////            )
////        }
//    }
//}
//var engines: WebEngine? = null
//var importAgain  =  mutableStateOf(0)
//@Composable
//fun ComposeJFXPanel(
//    composeWindow: ComposeWindow,
//    jfxPanel: JFXPanel,
//    onCreate: () -> Unit,
//    onDestroy: () -> Unit = {}
//) {
//    val jPanel = remember { JPanel() }
//    val density = LocalDensity.current.density
//    SwingPanel(
//        modifier = Modifier.fillMaxSize(),
//        factory = { jPanel },
//        update = {}
//    )
//
//    DisposableEffect(jPanel) {
//        composeWindow.add(jPanel)
//        jPanel.layout = BorderLayout(0, 0)
//        jPanel.add(jfxPanel)
//        onCreate()
//        onDispose {
//            onDestroy()
//            composeWindow.remove(jPanel)
//        }
//    }
//}
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun WebViewWindow(
//    onClose: () -> Unit
//) {
//    // Required to make sure the JavaFx event loop doesn't finish (can happen when java fx panels in app are shown/hidden)
//    val finishListener = object : PlatformImpl.FinishListener {
//        override fun idle(implicitExit: Boolean) {}
//        override fun exitCalled() {}
//    }
//    PlatformImpl.addListener(finishListener)
//
//    Window(
//        title = "通过网页导入",
//        resizable = true,
//        state = WindowState(
//            placement = WindowPlacement.Floating,
//        ),
//        onCloseRequest = {
//            PlatformImpl.removeListener(finishListener)
//            onClose()
//        },
//        content = {
//            val jfxPanel = remember { JFXPanel() }
//            var jsObject = remember<JSObject?> { null }
//
//            var nowUrl by rememberSaveable { mutableStateOf("加载中请稍后...") }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//            ){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ){
//                    remember { FocusRequester() }
//                    TextField(
//                        value = nowUrl,
//                        onValueChange = {
//                            nowUrl = it
////                            if (it=="\n" || it=="\t"){
////                                Platform.runLater{
////                                    engines?.load(nowUrl)
////                                }
////                            }
//                        },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .onPreviewKeyEvent {
//                                if (it.type == KeyEventType.KeyDown && it.key == Key.Enter){
//                                    Repository.logger.info { "键盘事件Enter" }
//                                    Platform.runLater{
//                                        engines?.load(nowUrl)
//                                    }
//                                }
//                                false
//                            }
//                            .weight(fill = true, weight = 1F)
//                    )
//                    TextButton(
//                        onClick = {
//                            Platform.runLater{
//                                engines?.load(nowUrl)
//                            }
//                        },
//                        content = {
//                            Text("跳转")
//                        }
//                    )
//                }
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1F)
//                        .background(Color.White)
//                ) {
//                    ComposeJFXPanel(
//                        composeWindow = window,
//                        jfxPanel = jfxPanel,
//                        onCreate = {
//                            Platform.runLater {
//                                val root = WebView()
//                                val engine = root.engine
//                                engines = engine
//                                val scene = Scene(root)
//                                engine.loadWorker.stateProperty().addListener { _, _, newState ->
//                                    if (newState === Worker.State.SUCCEEDED) {
//                                        jsObject = root.engine.executeScript("window") as JSObject
//                                        Repository.logger.info { "当前链接：${root.engine.location}" }
//                                        nowUrl = root.engine.location
//                                        engines = engine
//                                        // execute other javascript / setup js callbacks fields etc..
//                                    }
//                                }
//                                engine.loadWorker.exceptionProperty().addListener { _, _, newError ->
//                                    println("page load error : $newError")
//                                }
//                                jfxPanel.scene = scene
//                                engine.load("https://www.hbu.edu.cn/") // can be a html document from resources ..
//                                engine.setOnError { error -> println("onError : $error") }
//                            }
//                        },
//                        onDestroy = {
//                            Platform.runLater {
//                                val res = engines?.executeScript("document.documentElement.outerHTML")
////                                println(res)
//                                println(engines?.location)
//                                jsObject?.let { jsObj ->
//                                    // clean up code for more complex implementations i.e. removing javascript callbacks etc..
//                                }
//                            }
//                        },
//                    )
//                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(0.1F),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ){
//                    Button(
//                        onClick = {
//                            Platform.runLater{
//                                val res = engines?.executeScript("document.documentElement.outerHTML")
//                                Repository.logger.info("res")
//                                Repository.logger.info(res.toString())
//                            }
//                        }
//                    ) {
//                        Text("导入")
//                    }
//                }
//            }
//        })
//}