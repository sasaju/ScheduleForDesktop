package com.lifly.schedule.desktop.logic.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.observer.*
import kotlinx.coroutines.withTimeout

object NormalScheduleNetwork {
    private val baseUrl = "https://liflymark.top/"
    private val client = HttpClient(CIO){
        engine {
            requestTimeout = 8000
        }
        install(ResponseObserver) {
            onResponse { response ->
                println("HTTP status:"+ "${response.status.value}")
            }
        }
    }
    suspend fun getId() = CourseService.getId(baseUrl, client)

}