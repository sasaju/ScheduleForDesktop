package com.lifly.schedule.desktop.logic.network

import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.model.AllCourse
import com.lifly.schedule.desktop.logic.model.CourseResponse
import com.lifly.schedule.desktop.logic.model.IdResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object CourseService {
    suspend fun getId(
        baseUrl:String,
        client:HttpClient
    ): IdResponse {
        val url = baseUrl+"timetable/getid/"
        return client.get(url).body<IdResponse>()
    }

    suspend fun getCourse(
        baseUrl: String,
        client: HttpClient,
        user:String,
        password:String,
        id:String,
        yzm:String = "abcde"
    ): CourseResponse {
        val url = baseUrl+"timetable/"
        try{
            return client.submitForm(
                url = url,
                formParameters = Parameters.build {
                    append("user", user)
                    append("password", password)
                    append("headers", id)
                    append("yzm", yzm)
                }
            ).body()
        } catch (e:Exception){
            Repository.logger.info { e.toString() }
            return CourseResponse(
                status = "登录异常",
                allCourse = listOf(AllCourse("",0,0,"",0,"","", ""))
            )
        }
    }
}