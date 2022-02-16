package com.lifly.schedule.desktop.logic.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.json.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.withTimeout

object NormalScheduleNetwork {
    private const val baseUrl = "https://liflymark.top/"
    private val client = HttpClient(CIO){
        headersOf(HttpHeaders.UserAgent, "ktor-1.0")
        engine {
            requestTimeout = 8000
        }
        install(ResponseObserver) {
            onResponse { response ->
                println("HTTP status:"+ "${response.status.value}")
            }
        }
        install(ContentNegotiation){
            gson()
        }
        install(Logging)
    }

    suspend fun getId() = CourseService.getId(baseUrl, client)

    suspend fun getCourse(user:String, password:String, id:String, yzm:String="abcde") = CourseService.getCourse(baseUrl, client, user, password, id, yzm)

}