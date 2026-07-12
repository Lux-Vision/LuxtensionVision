package nl.luxtension.vision.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.luxtension.vision.ui.theme.LuxtensionAlert
import nl.luxtension.vision.ui.theme.LuxtensionOverlay

@Composable
fun CameraOverlay(
    cameraName: String,
    resolution: String,
    fps: String,
    recordingStatus: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(LuxtensionOverlay, RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OverlayItem("CAMERA", cameraName)
        OverlayItem("RES", resolution)
        OverlayItem("FPS", fps)
        OverlayItem("REC", recordingStatus, valueColor = LuxtensionAlert)
    }
}

@Composable
private fun OverlayItem(
    label: String,
    value: String,
    valueColor: Color = Color.White
) {
    Text(
        text = "$label  $value",
        color = valueColor,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold
    )
}
