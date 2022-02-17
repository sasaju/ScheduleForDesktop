package com.lifly.schedule.desktop.logic.dao

import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.model.CourseBean
import com.lifly.schedule.desktop.logic.model.CourseBeans
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.ReplaceStatement
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection


object CourseBeanDao {
    init {
        Repository.logger.info { "try crate table" }
        create()
    }
    private fun connect() {
        Database.connect("jdbc:sqlite:appData.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE
    }


    private fun create(){
        connect()
        transaction {
//            addLogger(StdOutSqlLogger)
            SchemaUtils.create(CourseBeans)
        }
    }
    fun insertCourseBeans(courseBeanList: List<CourseBean>): List<String> {
        connect()
        val a = transaction {
            courseBeanList.map { courseBean ->
                CourseBeans.replace {
                    it[campusName] = courseBean.campusName
                    it[classDay] = courseBean.classDay
                    it[classSessions] = courseBean.classSessions
                    it[classWeek] = courseBean.classWeek
                    it[continuingSession] = courseBean.continuingSession
                    it[courseName] = courseBean.courseName
                    it[teacher] = courseBean.teacher
                    it[teachingBuildName] = courseBean.teachingBuildName
                    it[color] = courseBean.color
                    it[colorIndex] = courseBean.colorIndex
                    it[removed] = courseBean.removed
                } get CourseBeans.courseName
            }
        }
        return a
    }

    fun loadAllCourseBeans(): List<CourseBean> {
        val a = transaction{
            SchemaUtils.create(CourseBeans)
            return@transaction CourseBeans.selectAll().map {
                CourseBean(
                    campusName = it[CourseBeans.campusName],
                    classDay = it[CourseBeans.classDay],
                    classSessions = it[CourseBeans.classSessions],
                    classWeek = it[CourseBeans.classWeek],
                    continuingSession = it[CourseBeans.continuingSession],
                    courseName = it[CourseBeans.courseName],
                    teacher = it[CourseBeans.teacher],
                    teachingBuildName = it[CourseBeans.teachingBuildName],
                    color = it[CourseBeans.color],
                    colorIndex = it[CourseBeans.colorIndex],
                    removed = it[CourseBeans.removed],
                )
            }
        }
        return a
    }
}