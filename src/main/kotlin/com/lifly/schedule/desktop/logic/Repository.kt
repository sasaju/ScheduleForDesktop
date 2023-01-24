package com.lifly.schedule.desktop.logic

import com.lifly.schedule.desktop.logic.dao.CourseBeanDao
import com.lifly.schedule.desktop.logic.model.*
import com.lifly.schedule.desktop.logic.network.NormalScheduleNetwork
import com.lifly.schedule.desktop.logic.util.Convert
import kotlinx.coroutines.flow.flow
import java.util.logging.Logger

object Repository {
    val logger: Logger = Logger.getLogger("test")
    fun getDefaultString() = listOf(
        listOf("#12c2e9", "#FFFC354C", "#FF0ABFBC"),
        listOf("#376B78", "#FFC04848", "#FF480048"),
        listOf("#f64f59", "#ff5f2c82", "#ff49a09d"),
        listOf("#CBA689", "#FFDC2424", "#DF5B69BE"),
        listOf("#ffffbb33", "#ff24C6DC", "#ff514A9D"),
        listOf("#8202F2", "#ffE55D87", "#ff5FC3E4"),
        listOf("#F77CC2", "#ff5C258D", "#ff4389A2"),
        listOf("#4b5cc4", "#ff134E5E", "#ff71B280"),
        listOf("#426666", "#ff085078", "#ff85D8CE"),
        listOf("#40de5a", "#ff4776E6", "#ff8E54E9"),
        listOf("#f0c239", "#5B59FF", "#D2934B"),
        listOf("#725e82", "#A91A2980", "#ff26D0CE"),
        listOf("#c32136", "#ffAA076B", "#E692088F"),
        listOf("#b35c44", "#FFFFA8C3", "#FFDCE083"),
    )
    fun getId() = flow {
        emit(NormalScheduleNetwork.getId().id)
    }
    suspend fun getId2() = NormalScheduleNetwork.getId()

    fun getCourse(user:String, passsword:String, id:String) = flow {
        val a = NormalScheduleNetwork.getCourse(user=user, password =passsword, id=id)
        emit(a)
    }

    suspend fun getCourse2(user: String, password: String, id: String) = NormalScheduleNetwork.getCourse(user, password, id)

    fun replaceCourseBeans(courseBeanList: List<CourseBean>): List<String> {
        return CourseBeanDao.insertCourseBeans(courseBeanList)
    }

    fun loadAllCourseToOneByOne(): List<List<OneByOneCourseBean>> {
        val allBean = CourseBeanDao.loadAllCourseBeans()
//        val allBean = listOf<CourseBean>()
        return Convert.courseBeanToOneByOne2(allBean, getDefaultString())
    }

    fun deleteAllCourseBean(){
        CourseBeanDao.clearCourseBean()
    }
}