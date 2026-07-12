package nl.luxtension.vision.ui.foundation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object LuxtensionColors {
    val Background = Color.Black
    val Surface = Color(0xFF101010)
    val Panel = Color(0xFF202020)
    val Control = Color.DarkGray
    val Accent = Color(0xFF1565C0)
    val TextPrimary = Color.White
    val TextSecondary = Color.Gray
    val Online = Color.Green
    val Offline = Color.Red
}

object LuxtensionDimens {
    val OuterPadding = 16.dp
    val CompactOuterPadding = 8.dp
    val BottomBarSpacing = 8.dp
    val BottomButtonMinWidth = 86.dp
    val SideMenuWidth = 260.dp
    val ZoomButtonWidth = 78.dp
    val ZoomButtonHeight = 42.dp
}

object LuxtensionStrings {
    const val AppTitle = "LUXTENSION VISION"
    const val Status = "● VISION BOX ONLINE   |   CAMERA: NIET GEKOPPELD"
    const val LiveWaiting = "WACHT OP CAMERABEELD"
    const val LiveInfo = "CAMERA 1  |  3840×2160  |  25 FPS  |  OFFLINE"
}
