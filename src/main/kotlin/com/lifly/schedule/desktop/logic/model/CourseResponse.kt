package com.lifly.schedule.desktop.logic.model

data class CourseResponse(
    val allCourse: List<AllCourse>,
    val status: String
)

data class AllCourse(
    val campusName: String,
    val classDay: Int,
    val classSessions: Int,
    val classWeek: String,
    val continuingSession: Int,
    val courseName: String,
    val teacher: String,
    val teachingBuildName: String
)