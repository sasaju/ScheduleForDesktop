import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState

val LocalWindowsState =
    staticCompositionLocalOf {
        WindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(850.dp, 836.dp)
        )
    }

val LocalShowWidgetState =
    staticCompositionLocalOf {
        mutableStateOf(true)
    }

val LocalWidgetStateNotitle =
    staticCompositionLocalOf {
        WindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(400.dp, 400.dp)
        )
    }