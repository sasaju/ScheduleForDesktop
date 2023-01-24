package com.lifly.schedule.desktop.ui.import_timetable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.model.CourseResponse
import kotlinx.coroutines.launch

@Composable
fun Login(
    id: String,
    loginText: String,
    onSuccess:(result: CourseResponse) -> Unit
){
    var nowLoginStatus by rememberSaveable { mutableStateOf(loginText) }
    val scope = rememberCoroutineScope()
    var showLoad by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(id){
        if (id==""){
            showLoad = false
            nowLoginStatus = "正在加载"
        }else{
            showLoad = true
            nowLoginStatus = "登录"
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SignUIAll { user, password ->
            Button(
                enabled = showLoad,
                onClick = {
                    scope.launch {
                        showLoad = false
                        nowLoginStatus= "正在加载"
                        val thisId = Repository.getId2().id
                        val result = Repository.getCourse2(user, password, thisId)
                        onSuccess(result)
                        showLoad = true
                        nowLoginStatus = "登录"
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = nowLoginStatus, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
            }
        }
    }
}
@Composable
fun SignUIAll(
    loginButton: @Composable() ((user: String, password: String) -> Unit)?
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .requiredWidthIn(min = 50.dp, max = 500.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.TopCenter,
        propagateMinConstraints = true
    ) {
        val imageHeight = 60
        val spaceHeight = 50
        Column(
        ) {
            Spacer(modifier = Modifier.height((imageHeight/2 + spaceHeight).dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(2.dp)
                    .alpha(0.9f),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.2.dp, Color(0xFF80D4FF)),
                backgroundColor = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight()
                ) {
                    LoginAccountPassword("",""){ user, password ->
                        if (loginButton != null) {
                            loginButton(user, password)
                        }
//                        Button(
//                            onClick = { /*TODO*/ },
//                            shape = RoundedCornerShape(20.dp),
//                            modifier = Modifier.fillMaxWidth(0.8f)
//                        ) {
//                            Row(
//                                modifier =  Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                "登录".split("").forEach {
//                                    Text(text = it, style = MaterialTheme.typography.h6, maxLines = 1)
//                                }
//                            }
//                        }
//                        Spacer(modifier = Modifier.height(10.dp))
//                        OutlinedButton(
//                            onClick = { /*TODO*/ },
//                            shape = RoundedCornerShape(20.dp),
//                            modifier = Modifier.fillMaxWidth(0.8f)
//                        ) {
//                            Row(
//                                modifier =  Modifier.fillMaxWidth(0.8f),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                "查看离线数据".split("").forEach {
//                                    Text(text = it, style = MaterialTheme.typography.h6, maxLines = 1)
//                                }
//                            }
//                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.background(Color.Transparent)
        ){
            Spacer(modifier = Modifier.height(spaceHeight.dp))
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .clip(CircleShape),
                propagateMinConstraints = false
            ) {
                Image(
                    painter = painterResource("drawable/ic_launcher-playstore.png"),
                    modifier = Modifier
                        .size(60.dp),
                    contentDescription = null
                )
            }
        }
    }

}
@Composable
fun LoginAccountPassword(
    userInit:String,
    passwordInit:String,
    loginButton:@Composable ((user:String, password:String) -> Unit)?,
){
//    val viewModel:SignUIViewModel = viewModel()
//    val accountSate = viewModel.accountFlow.collectAsState(initial = mapOf("user" to "", "password" to ""))
    var user by rememberSaveable{ mutableStateOf(userInit) }
    var password by rememberSaveable { mutableStateOf(passwordInit) }
    var visual by remember { mutableStateOf<VisualTransformation>(PasswordVisualTransformation()) }
    var visualIcon by remember { mutableStateOf(Icons.Default.Visibility) }
//    LaunchedEffect(accountSate.value){
//        viewModel.passwordSate.value = accountSate.value["password"].toString()
//        viewModel.userSate.value = accountSate.value["user"].toString()
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "登录URP教务系统", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(20.dp))
        LoginTextFiled(
            onValueChange = {user = it},
            value = user,
            label = { Text("请输入学号") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.95f),
            leadingIcon = {Icon(imageVector = Icons.Default.Person, contentDescription = "学号")}
        )
        Spacer(modifier = Modifier.height(20.dp))
        LoginTextFiled(
            onValueChange = {password = it},
            value = password,
            label = { Text(text = "请输入密码")},
            visualTransformation = visual,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(0.95f),
            leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = "密码")},
            trailingIcon = {
                IconButton(onClick = {
                    visual = if (visual== VisualTransformation.None) { PasswordVisualTransformation() } else { VisualTransformation.None}
                    visualIcon = if (visualIcon == Icons.Default.VisibilityOff){ Icons.Default.Visibility }else{Icons.Default.VisibilityOff}
                }) {
                    Icon(imageVector = visualIcon, contentDescription = "查看")
                }
            }
        )
        if (loginButton != null) {
            Spacer(modifier = Modifier.height(40.dp))
            loginButton(user, password)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun LoginTextFiled(
    modifier: Modifier=Modifier,
    value:String="",
    onValueChange:(value:String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        leadingIcon=leadingIcon,
        trailingIcon = trailingIcon,
        label= label,
        placeholder = placeholder,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.bodyLarge
    )
}