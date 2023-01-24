package com.lifly.schedule.desktop.logic.util


import androidx.compose.ui.graphics.Color
import com.lifly.schedule.desktop.logic.model.*
import kotlin.math.sin

object Convert {
    val String.color
        get() = Color(
            Integer.valueOf( this.substring( 1, 3 ), 16 ),
            Integer.valueOf( this.substring( 3, 5 ), 16 ),
            Integer.valueOf( this.substring( 5, 7 ), 16 )
        )


    fun courseResponseToBean(
        allCourse: List<AllCourse>
    ) = allCourse.map { courseResponse ->
            val color = stringToColor(courseResponse.courseName)
            CourseBean(
                courseResponse.campusName,
                courseResponse.classDay,
                courseResponse.classSessions,
                courseResponse.classWeek,
                courseResponse.continuingSession,
                courseResponse.courseName,
                courseResponse.teacher,
                courseResponse.teachingBuildName,
                color,
                colorIndex = courseNameToIndex(courseResponse.courseName, colorListLength = 13)
            )
        }
    fun courseBeanToOneByOne2(courseBeanList: List<CourseBean>, colorList:List<List<String>>): List<List<OneByOneCourseBean>> {
        val allWeekList = mutableListOf<MutableList<OneByOneCourseBean>>()
        val maxWeek = courseBeanList.getOrElse(0) { getInitial()[0] }.classWeek.length
        repeat(maxWeek) {
            val singleWeekList = mutableListOf<OneByOneCourseBean>()
            allWeekList.add(singleWeekList)
        }
        for (i in 0 until maxWeek) {
            for (courseBean in courseBeanList) {
                val name =
                    courseBean.courseName + "\n" + courseBean.teachingBuildName + "\n" + courseBean.teacher
                when (courseBean.classWeek[i].toString()) {
                    "1" -> {
                        // index<0为兼容老版本APP， removed代表是否无视主题色强行应用用户自己设置的颜色，remove目前只能配置纯色颜色
                        val twoColorList =
                            when {
                                courseBean.removed -> {
                                    listOf(
                                        courseBean.color.color,
                                        (colorList[courseBean.colorIndex].getOrNull(1)?:courseBean.color).color,
                                        (colorList[courseBean.colorIndex].getOrNull(2)?:courseBean.color).color,
                                    )
                                }
                                else -> {
                                    listOf(
                                        colorList[courseBean.colorIndex][0].color,
                                        (colorList[courseBean.colorIndex].getOrNull(1)?:courseBean.color).color,
                                        (colorList[courseBean.colorIndex].getOrNull(2)?:courseBean.color).color,
                                    )
                                }
                            }
                        val a = OneByOneCourseBean(
                            courseName = name,
                            start = courseBean.classSessions,
                            end = courseBean.classSessions + courseBean.continuingSession - 1,
                            whichColumn = courseBean.classDay,
                            color = courseBean.color.color,
                            twoColorList = twoColorList
                        )
                        allWeekList[i].add(a)
                    }
                }
            }
        }
        allWeekList
        return allWeekList
    }
    fun colorStringToInt(colorString: String): Int{
        var colorStr = colorString.replace("#", "")
        while (true){
            if (colorStr.length >= 8)
                break
            colorStr = "f$colorStr"
        }
        return colorStr.toLong(16).toInt()
    }

    fun stringToColor(name: String): String {
        val colorList = arrayListOf<String>()
        colorList.apply {
            add("#12c2e9")
            add("#376B78")
            add("#f64f59")
            add("#CBA689")
            add("#ffffbb33")
            add("#8202F2")
            add("#F77CC2")
            add("#4b5cc4")
            add("#426666")
            add("#40de5a")
            add("#f0c239")
            add("#725e82")
            add("#c32136")
            add("#b35c44")
        }
        return try {
            val num = string2Unicode(name).toInt()
            colorList[num % colorList.count()]
        } catch (e: Exception) {
            colorList[0]
        }
    }
    fun string2Unicode(string: String): String {
        val unicode = StringBuffer()
        for (i in string.indices) {
            // 取出每一个字符
            val c = string[i]
            // 转换为unicode
            // unicode.append("\\u" + Integer.toHexString(c.toInt()))
            unicode.append(Integer.toHexString(c.toInt())[0])
            if (i > 2) {
                break
            }
        }
        return unicode.toString()
    }
    fun courseNameToIndex(name: String, colorListLength:Int):Int{
        return try {
            val num = string2Unicode(name).toInt()
            num % colorListLength
        } catch (e: Exception) {
            0
        }
    }

}