package com.lifly.schedule.desktop.logic.model

import androidx.compose.ui.graphics.Color

/**课程表页面真正显示时拿到的类
 * @param color 这是个历史遗留值，这个值是因为在显示班级课程时需要用到，我懒得修改代码所以没有移除它
 * @param twoColorList 事实上它有三个item，第一个为纯色时的配色，第二、三个为它渐变色时的起止色
 *
 * 如果你希望更改它，请特别小心
 *
 * 同时它并不是一个好的写法，它同时调用了Repository和Convert单例类
 */

data class OneByOneCourseBean(
    val courseName: String,
    val start: Int,
    val end: Int,
    val whichColumn: Int,
    val color: Color,
    val twoColorList: List<Color>
)

fun getData() = OneByOneCourseBean("药物化学\n第九教学楼205\n张三*", 1, 2, 1, Color.Gray, listOf(Color.Gray, Color.Gray, Color.Gray))