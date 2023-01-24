package com.lifly.schedule.desktop.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef.HWND

interface User32Lib : Library {
    fun FindWindowA(lpClassName: String, lpWindowName:String?):HWND?
    fun SetParent(hWndChild:HWND, hWndNewParent:HWND):HWND
}

@Composable
fun WeekWidget(){
    LaunchedEffect(Unit){
        val user32Lib :User32Lib = Native.load("user32", User32Lib::class.java)
        val thisHWND = user32Lib.FindWindowA("SunAwtFrame", "课程表")
        println(thisHWND.toString())
    }
}