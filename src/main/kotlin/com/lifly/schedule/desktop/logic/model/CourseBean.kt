package com.lifly.schedule.desktop.logic.model

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


/** 课程data类
 * APP中极为关键的一个类
 * @param campusName 校区
 * @param color 颜色配置，实例：#ff51555
 * @param colorIndex 为适配主题色，配置一个数字，数字代表一个colorList中的index,[[a,b,c], [a,b,c]]
 * @param removed 代表是否移除主题色的限制，如果为false-0，则使用colorIndex作为颜色依据，如果为true-1，则使用color作为颜色依据
 *
 * 不过无论是开发难度和用户操作难度，实现脱离主题配置都十分的不容易，所以暂时放弃。
 */

data class CourseBean(
    var campusName: String,
    var classDay: Int,
    var classSessions: Int,
    var classWeek: String,
    var continuingSession: Int,
    var courseName: String,
    var teacher: String,
    var teachingBuildName: String,
    var color: String,
    var colorIndex: Int = 0,
    var removed: Boolean = false
)


object CourseBeans:Table(){
    var campusName: Column<String> = varchar("campusName",20)
    var classDay:Column<Int> = integer("classDay")
    var classSessions: Column<Int> = integer("classSessions")
    var classWeek: Column<String> = varchar("classWeek",50)
    var continuingSession:  Column<Int> = integer("continuingSession")
    var courseName: Column<String> = varchar("courseName",50)
    var teacher: Column<String> = varchar("teacher",50)
    var teachingBuildName: Column<String> = varchar("teachingBuildName",50)
    var color: Column<String> = varchar("color",50)
    var colorIndex:  Column<Int> = integer("colorIndex")
    var removed: Column<Boolean> = bool("removed")
    override val primaryKey: PrimaryKey = PrimaryKey(classDay, classSessions, classWeek, continuingSession, courseName, teacher, teachingBuildName, color, colorIndex, removed)
}


fun getInitial(): List<CourseBean> {
    return listOf(CourseBean(
        campusName="五四路校区",
        classDay=3,
        classSessions=9, classWeek="111111111111110000000000", continuingSession=3,
        courseName="毛泽东思想与中国特色社会主义理论概论",
        teacher="刘卫萍* 耿金龙 ",
        teachingBuildName="第九教学楼402",
        color="#f0c239"))
}
