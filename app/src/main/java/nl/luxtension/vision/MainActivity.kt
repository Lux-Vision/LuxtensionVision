package nl.luxtension.vision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.luxtension.vision.ui.theme.LuxtensionVisionTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.delay
import nl.luxtension.vision.ui.screens.LiveViewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuxtensionVisionTheme {
                LiveViewScreen()
            }
        }
    }
}

@Composable
fun OldLiveViewScreen() {
    val currentTime = remember { mutableStateOf(currentDateTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = currentDateTime()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        TopBar(currentTime.value)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF1F1F1F)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LIVE CAMERABEELD",
                color = Color.Gray,
                fontSize = 28.sp
            )

            HomeButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )

            ZoomControls(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
        }

        BottomBar()
    }
}

@Composable
fun TopBar(timeText: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF202020))
            .padding(16.dp)
    ) {
        Text(
            text = "LUXTENSION VISION",
            color = Color.White,
            fontSize = 22.sp
        )
        Text(
            text = timeText,
            color = Color.LightGray,
            fontSize = 16.sp
        )
    }
}

@Composable
fun HomeButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier.size(64.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1565C0)
        )
    ) {
        Text(
            text = "⌂",
            color = Color.White,
            fontSize = 26.sp
        )
    }
}

@Composable
fun ZoomControls(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listOf("1×", "4×", "8×", "MAX").forEach { label ->
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF303030)
                )
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(Color(0xFF202020))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomButton("🌡️")
        BottomButton("👤")
        BottomButton("🌍")
        BottomButton("📷")
        BottomButton("⚙️")
    }
}

@Composable
fun BottomButton(label: String) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF303030)
        )
    ) {
        Text(
            text = label,
            fontSize = 24.sp
        )
    }
}

fun currentDateTime(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy   HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}