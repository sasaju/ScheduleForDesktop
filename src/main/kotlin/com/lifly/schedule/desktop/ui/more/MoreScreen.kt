package com.lifly.schedule.desktop.ui.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lifly.schedule.desktop.logic.Repository
import com.lifly.schedule.desktop.logic.util.pager.ExperimentalPagerApi
import com.lifly.schedule.desktop.logic.util.pager.HorizontalPager
import com.lifly.schedule.desktop.logic.util.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ShowMore(text: String) {
    val pagerState = rememberPagerState(initialPage = 2)
    val scope = rememberCoroutineScope()
    HorizontalPager(
        state = pagerState,
        count = 8,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
//            Text("$page", modifier = Modifier.clickable {
//                scope.launch {
//                    pagerState.animateScrollToPage(page+1)
//                }
//            })
            Text(text)
        }
    }
}