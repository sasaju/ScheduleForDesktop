package com.lifly.schedule.desktop.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifly.schedule.desktop.logic.dao.SettingsSerializer
import com.lifly.schedule.desktop.logic.model.OneByOneCourseBean
import com.lifly.schedule.desktop.logic.model.getData
import java.lang.module.ModuleFinder


@Composable
fun Test(){
    val a = rememberSaveable { SettingsSerializer.appSettings.toString() }
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
fun SingleClass2(
    singleClass: OneByOneCourseBean,
    conflict: Boolean = false,
    courseClick:(oneByOne:OneByOneCourseBean) -> Unit = {}
) {
//    val context = LocalContext.current
//    val activity = (LocalContext.current as? Activity)
//    val interactionSource = remember { MutableInteractionSource() }
    val perHeight = 80
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