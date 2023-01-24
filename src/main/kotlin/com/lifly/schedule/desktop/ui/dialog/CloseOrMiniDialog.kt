package com.lifly.schedule.desktop.ui.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import com.lifly.schedule.desktop.theme.ScheduleTheme

@Composable
fun MinOrCloseDialog(
    visible:Boolean,
    close:() -> Unit,
    min:() -> Unit,
    exit:() -> Unit
){
    var closeIt:CloseIt by remember { mutableStateOf(CloseIt.Exit) }
    Dialog(
        visible = visible,
        onCloseRequest = { close() },
        resizable = false,
        title = "关闭课程表",
        state = DialogState(width = 300.dp, height = 210.dp)
    ){
        ScheduleTheme{
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                RadioTextButton(
                    modifier = Modifier.width(200.dp),
                    selected = closeIt == CloseIt.Exit,
                    onClick = { closeIt = CloseIt.Exit },
                    text = "退出"
                )
                RadioTextButton(
                    modifier = Modifier.width(200.dp),
                    selected = closeIt == CloseIt.Mini,
                    onClick = { closeIt = CloseIt.Mini },
                    text = "最小化至托盘"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    ,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            when (closeIt) {
                                CloseIt.Mini -> min()
                                CloseIt.Exit -> exit()
                            }
                        }
                    ) {
                        Text("确认")
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Button(
                        onClick = {
                            close()
                        }
                    ) {
                        Text("取消")
                    }
                }
            }
        }
    }
}

@Composable
fun RadioTextButton(
    modifier: Modifier = Modifier,
    selected:Boolean,
    onClick:() -> Unit,
    text:String = ""
){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected =selected,
            onClick = onClick,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(10.dp))
    }
}

sealed class CloseIt(){
    object Exit:CloseIt()
    object Mini:CloseIt()
}