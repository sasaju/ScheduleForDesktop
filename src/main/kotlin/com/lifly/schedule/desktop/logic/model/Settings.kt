package com.lifly.schedule.desktop.logic.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppSettings(
    var newUser:Boolean = true,
    var userVersion: Int = 0,
)
