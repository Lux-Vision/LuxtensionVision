package nl.luxtension.vision.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.luxtension.vision.ui.theme.LuxtensionBorder

@Composable
fun CameraViewport(
    modifier: Modifier = Modifier,
    cameraName: String = "Camera 1",
    resolution: String = "3840 × 2160",
    fps: String = "25 FPS",
    recordingStatus: String = "OFF"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Black)
            .border(1.dp, LuxtensionBorder, RoundedCornerShape(14.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "▣", color = Color.DarkGray, fontSize = 56.sp)
            Text(
                text = "Waiting for camera connection...",
                color = Color.LightGray,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        CameraOverlay(
            cameraName = cameraName,
            resolution = resolution,
            fps = fps,
            recordingStatus = recordingStatus,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
