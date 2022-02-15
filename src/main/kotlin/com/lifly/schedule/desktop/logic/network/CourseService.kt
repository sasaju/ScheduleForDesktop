package com.lifly.schedule.desktop.logic.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

object CourseService {
    suspend fun getId(
        baseUrl:String,
        client:HttpClient
    ): String {
        val url = baseUrl+"timetable/getid/"
        return client.get(url).body<String>()
    }
}