package com.lifly.schedule.desktop.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Snackbar
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.Repository.logger
import com.lifly.schedule.desktop.logic.dao.SettingsSerializer
import com.lifly.schedule.desktop.logic.model.AppSettings
import com.lifly.schedule.desktop.logic.model.OneByOneCourseBean
import com.lifly.schedule.desktop.logic.util.GetDataUtil
import com.lifly.schedule.desktop.logic.util.flowlayout.FlowRow
import com.lifly.schedule.desktop.logic.util.flowlayout.MainAxisAlignment
import com.lifly.schedule.desktop.logic.util.pager.ExperimentalPagerApi
import com.lifly.schedule.desktop.logic.util.pager.HorizontalPager
import com.lifly.schedule.desktop.logic.util.pager.PagerState
import com.lifly.schedule.desktop.logic.util.pager.rememberPagerState
import com.lifly.schedule.desktop.ui.show_timetable.*
import kotlinx.coroutines.launch

private val maxWeek = 19
private fun Int.nowWeek() = if(this<maxWeek){this}else{0}
@Composable
fun ShowTimeTableAll(){
    val scope = rememberCoroutineScope()
    lateinit var settings: AppSettings
    LaunchedEffect(Unit){
        settings= SettingsSerializer.appSettings
        logger.info { settings.userVersion.toString() }
    }
    ShowCourseAndNowWeek()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowCourseAndNowWeek(){
    val nowWeek = GetDataUtil.whichWeekNow().nowWeek()
    val state = rememberPagerState(initialPage = nowWeek)
    suspend fun jumpPage(target:Int){
        when{
            target>state.pageCount-1 -> {
//                state.animateScrollToPage(0)
            }
            target<0 ->{
//                state.animateScrollToPage(state.pageCount-1)
            }
            else -> {
                state.animateScrollToPage(target)
            }
        }
    }
    BoxWithConstraints {
        val maxWidth = maxWidth
        Row{
            if (maxWidth.value > 720F) {
                Box(
                    modifier = Modifier.weight(1F)
                ){
                    ShowNowWeek(nowWeek, pagerState = state)
                }
            }
            Box(
                modifier = Modifier.weight(5F)
            ){
                ShowCourse(state)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowNowWeek(
    nowWeek:Int,
    pagerState:PagerState
){
    val nowWeekOrNot = (pagerState.currentPage == nowWeek)
    val scope = rememberCoroutineScope()
    val userNowWeek = remember(pagerState.currentPage) { mutableStateOf(pagerState.currentPage+1) }
    val startSchoolOrNot = GetDataUtil.startSchool()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    scope.launch { pagerState.animateScrollToPage(nowWeek) }
                }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "第${userNowWeek.value}周", style = MaterialTheme.typography.titleLarge)
            }
            if (nowWeekOrNot && startSchoolOrNot) {
                Text(text = "当前周", fontSize = 16.sp)
            } else if (!startSchoolOrNot) {
                Text(
                    text = "距离开课\n${-GetDataUtil.startSchoolDay()}天",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
            if (GetDataUtil.whichWeekNow() > maxWeek) {
                Text(text = "放假了，联系开发者更新", fontSize = 16.sp, color = Color.Gray)
            }
        }

        FlowRow(
            modifier = Modifier
                .padding(10.dp)
                .height(265.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            mainAxisAlignment = MainAxisAlignment.Center
        ) {
            repeat(maxWeek){
                TextButton(
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(it) }
                    }
                ){
                    Text("第${it+1}周", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Text(
            "可以点击上述周数快速跳转\n\n也可以使用“←”和“→”（方向键左右）跳转下一周",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }

}


@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ShowCourse(state:PagerState){
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }
    suspend fun jumpPage(target:Int){
        when{
            target>state.pageCount-1 -> {
//                state.animateScrollToPage(0)
            }
            target<0 ->{
//                state.animateScrollToPage(state.pageCount-1)
            }
            else -> {
                focusRequester.requestFocus()
                state.animateScrollToPage(target)
            }
        }
    }
    Box(
        modifier = Modifier
            .focusRequester(focusRequester)
            .onPreviewKeyEvent {
                if (it.type== KeyEventType.KeyDown){
                    when (it.key) {
                        Key.DirectionRight -> {
                            scope.launch { jumpPage(state.targetPage + 1) }
                            return@onPreviewKeyEvent true
                        }
                        Key.DirectionLeft -> {
                            scope.launch { jumpPage(state.targetPage - 1) }
                            return@onPreviewKeyEvent true
                        }
                        Key.DirectionUp -> {
                            scope.launch { jumpPage(state.targetPage -1) }
                            return@onPreviewKeyEvent true
                        }
                        Key.DirectionDown -> {
                            scope.launch { jumpPage(state.targetPage + 1) }
                            return@onPreviewKeyEvent true
                        }
                        else -> return@onPreviewKeyEvent false
                    }
                }else{
                    return@onPreviewKeyEvent false
                }
            }
    ){ Spacer(modifier = Modifier.size(1.dp).focusable()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .width(30.dp)
                .fillMaxHeight()
                .clickable {
                    scope.launch { jumpPage(state.targetPage-1) }
                }
            ,
            contentAlignment = Alignment.Center
        ){
            Icon(Icons.Default.ArrowBack, null)
        }
        HorizontalPager(
            state=state,
            count = maxWeek,
            modifier = Modifier
                .weight(1F, true)
        ) { page: Int ->
            SingleLineClass(
                Repository.loadAllCourseToOneByOne(),
                page,
            ) {}
        }
        Box(
            modifier = Modifier
                .width(30.dp)
                .fillMaxHeight()
                .clickable {
                    scope.launch { jumpPage(state.targetPage+1) }
                }
            ,
            contentAlignment = Alignment.Center
        ){
            Icon(Icons.Default.ArrowForward, null)
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
                    fontSize = 15.sp, textAlign = TextAlign.Center
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
                            fontSize = 15.sp,
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
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
        }


        Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(Modifier.weight(0.64F, true)) {
                // 时间列
                repeat(11) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = ParagraphStyle(lineHeight = 6.sp)) {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                ) {
                                    append("${it + 1}")
                                }
                            }
                            withStyle(style = SpanStyle(fontSize = 14.sp)) {
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