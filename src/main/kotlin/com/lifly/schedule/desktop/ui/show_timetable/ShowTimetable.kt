package com.lifly.schedule.desktop.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Snackbar
import androidx.compose.material.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifly.schedule.desktop.logic.model.OneByOneCourseBean
import com.lifly.schedule.desktop.logic.model.getData
import com.lifly.schedule.desktop.ui.show_timetable.*


@Composable
fun Test(){
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            repeat(7){ colIndex ->
                Column(modifier = Modifier.fillMaxWidth().weight(1F)){
                    SingleClass2(getData()) {
                        println(it.whichColumn)
                    }
                    if (colIndex == 2) {
                        SingleClass2(getData()) {
                            println(it.whichColumn)
                        }
                        SingleClass2(getData()) {
                            println(it.whichColumn)
                        }
                        SingleClass2(getData()) {
                            println(it.whichColumn)
                        }

                        SingleClass2(getData()) {
                            println(it.whichColumn)
                        }

                    }
                }
            }
        }
    }
}
@Composable
fun SingleLineClass(
    oneWeekClass: List<List<OneByOneCourseBean>>,
    page: Int,
    courseClick: (oneByOne: OneByOneCourseBean) -> Unit
) {
    val perHeight = 90
    val mode = 0
//    val iconColor = if (!settings.darkShowBack) {
//        MaterialTheme.colors.onBackground
//    } else {
//        Color.Black
//    }
    val snackbarVisibleState = remember { mutableStateOf(false) }
    val snackbarVisibleShowState = remember { mutableStateOf(true) }
    var snackbarText by remember { mutableStateOf("") }
    Column {
        // snackbar
        if (snackbarVisibleState.value && snackbarVisibleShowState.value) {
            Snackbar(
                action = {
                    TextButton(onClick = {
                        snackbarVisibleState.value = false
                        snackbarVisibleShowState.value = false
                    }) {
                        Text("关闭提示")
                    }
                },
                modifier = Modifier
                    .padding(4.dp)
                    .alpha(0.8f)
            ) { Text(text = snackbarText) }
        }
        //星期行
        Row {
            Column(
                Modifier
                    .weight(0.6F)
                    .height(60.dp)
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${getDayOfDate(0, page)} 月",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 10.sp, textAlign = TextAlign.Center
                )
            }

            repeat(7) {
                val textText = "${getDayOfWeek(it + 1)}\n${getDayOfDate(it + 1, page)}"
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1F, true)
                        .height(60.dp),
                    color = Color.Transparent
                ) {
                    if (!isSelected(it + 1, page)) {
                        Text(
                            text = textText,
                            modifier = Modifier.background(Color.Transparent),
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = textText,
                            modifier = Modifier
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Blue,
                                            Color.Transparent
                                        )
                                    )
                                ),
                            fontSize = 11.sp,
                            lineHeight = 10.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }


        Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(Modifier.weight(0.6F, true)) {
                // 时间列
                repeat(11) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = ParagraphStyle(lineHeight = 6.sp)) {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                ) {
                                    append("${it + 1}")
                                }
                            }
                            withStyle(style = SpanStyle(fontSize = 10.sp)) {
                                append("${getStartTime(it + 1)}\n")
                                append(getEndTime(it + 1))
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(perHeight.dp),
                        textAlign = TextAlign.Center,

                        )
                }
            }

            // 课程
            val realOneWeekList = getNeededClassList(oneWeekClass[page])


            for (oneDayClass in realOneWeekList) {
                var count = 0
                val nowJieShu = IntArray(12) { it + 1 }.toMutableList()
                Column(Modifier.weight(1F, true)) {
                    for (oneClass in oneDayClass) {
                        val spacerHeight = (oneClass.start - nowJieShu.getOrElse(0){1}) * perHeight

                        if (spacerHeight < 0) {
                            val nowClassAllName = oneClass.courseName.split("\n")
                            val nowClassName = nowClassAllName.getOrElse(0) { "" }
                            val nowClassBuild = nowClassAllName.getOrElse(1) { "" }
                            val lastClassAllName = oneDayClass[count - 1].courseName.split("\n")
                            val lastClassName = lastClassAllName.getOrElse(0) { "" }
                            val lastClassBuild = lastClassAllName.getOrElse(1) { "" }
                            snackbarText = "检测到${nowClassAllName[0]}课程冲突，务必仔细检查"
                            snackbarVisibleState.value = true
                            key(oneClass.courseName + oneClass.start + oneClass.end) {
                                SingleClass2(
                                    singleClass = oneClass,
                                    conflict = true
                                ) {
                                    courseClick(it)
                                }
                            }
                            continue
                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(spacerHeight.dp)
                        )
                        key(oneClass.courseName + oneClass.start + oneClass.end) {
                            SingleClass2(
                                singleClass = oneClass,
                            ) {
                                courseClick(it)
                            }
                        }
                        nowJieShu -= IntArray(oneClass.end) { it + 1 }.toMutableList()
                        count += 1
                    }
                }
            }
        }
    }
}
@Composable
fun SingleClass2(
    singleClass: OneByOneCourseBean,
    conflict: Boolean = false,
    courseClick:(oneByOne:OneByOneCourseBean) -> Unit = {}
) {
//    val context = LocalContext.current
//    val activity = (LocalContext.current as? Activity)
//    val interactionSource = remember { MutableInteractionSource() }
    val perHeight = 90
    val mode = 0
    val borderWidth = 1
    val courseNameSize = 20
    val courseTeacherSize = 15
    val courseAlpha = 0.9F
    val height =
        if (!conflict) {
            perHeight * (singleClass.end - singleClass.start + 1)
        } else {
            (perHeight * (singleClass.end - singleClass.start + 1) * 0.6).toInt()
        }
    val nameList = singleClass.courseName.split("\n")
    val cardColor =
        if (mode==0){
            listOf(singleClass.twoColorList[0], singleClass.twoColorList[0])
        } else{
            listOf(singleClass.twoColorList[1], singleClass.twoColorList[2])
        }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .padding(0.95.dp) // 外边距
                .alpha(courseAlpha),
            shape = RoundedCornerShape(4.dp)
        ) {

            //        val showDetailDialog = remember { mutableStateOf(false) }
            //        ClassDetailDialog(openDialog = showDetailDialog, singleClass = singleClass)
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.White,
                            fontSize = courseNameSize.sp
                        )
                    ) {
                        append(nameList[0] + "\n" + nameList[1] + "\n\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W500,
                            color = Color.White,
                            fontSize = courseTeacherSize.sp
                        )
                    ) {
                        append(nameList[2])
                    }
                },
                modifier = Modifier
                    .background(Brush.verticalGradient(cardColor))
                    .padding(horizontal = (borderWidth + 1).dp, vertical = borderWidth.dp)
                    .clickable {
                        //                    showDetailDialog.value = true
                        courseClick(singleClass)
                    },
                textAlign = TextAlign.Center

            )
        }
    }
}