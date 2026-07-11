package nl.luxtension.vision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LiveViewScreen() {
    var activeScreen by remember { mutableStateOf("LIVE") }
    var menuOpen by remember { mutableStateOf(false) }

    var currentDate by remember { mutableStateOf("") }
    var currentTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            currentDate =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

            currentTime =
                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 72.dp,
                    bottom = 12.dp
                )
        ) {
            Header(
                date = currentDate,
                time = currentTime,
                onMenuClick = { menuOpen = !menuOpen }
            )

            Text(
                text = "● VISION BOX ONLINE   |   CAMERA: NIET GEKOPPELD",
                color = Color.Green,
                fontSize = 13.sp
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF101010)),
                contentAlignment = Alignment.Center
            ) {
                when (activeScreen) {
                    "LIVE" -> LiveContent()

                    "THERM" -> SimpleScreen(
                        "THERMISCH BEELD",
                        "Warmtebron detectie voorbereid"
                    )

                    "AI" -> SimpleScreen(
                        "AI DETECTIE",
                        "Personen en voertuigen detectie voorbereid"
                    )

                    "MAP" -> SimpleScreen(
                        "VOERTUIG LOCATIE",
                        "GPS kaart voorbereid"
                    )

                    "FOTO" -> SimpleScreen(
                        "FOTO'S EN OPNAMES",
                        "Snapshots en video-opnames voorbereid"
                    )

                    "INSTEL" -> SettingsScreen()

                    "CAMERAS" -> SettingsScreen()

                    "CLOUD" -> SimpleScreen(
                        "CLOUD",
                        "Cloud opslag voorbereid"
                    )

                    "USERS" -> SimpleScreen(
                        "GEBRUIKERS",
                        "Beheerder, gebruiker en kijker voorbereid"
                    )

                    "VEHICLE" -> SimpleScreen(
                        "VOERTUIG",
                        "Mast, GPS en voertuigdata voorbereid"
                    )

                    "TAAL" -> SimpleScreen(
                        "TAAL",
                        "Nederlands, Engels en Duits voorbereid"
                    )

                    "OVER" -> SimpleScreen(
                        "OVER",
                        "Luxtension Vision versie 0.1"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            BottomBar(
                active = activeScreen,
                onSelect = { activeScreen = it }
            )
        }

        if (menuOpen) {
            SideMenu(
                onSelect = {
                    activeScreen = it
                    menuOpen = false
                }
            )
        }
    }
}

@Composable
fun Header(
    date: String,
    time: String,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onMenuClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "☰",
                    color = Color.White,
                    fontSize = 26.sp
                )
            }

            Text(
                text = "LUXTENSION VISION",
                color = Color.White,
                fontSize = 22.sp
            )
        }

        Text(
            text = "$date   $time",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun LiveContent() {
    Text(
        text = "WACHT OP CAMERABEELD",
        color = Color.Gray,
        fontSize = 28.sp
    )

    Text(
        text = "CAMERA 1  |  3840×2160  |  25 FPS  |  OFFLINE",
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .wrapContentSize(Alignment.BottomStart)
    )

    ZoomButtons()
}

@Composable
fun SimpleScreen(
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = subtitle,
            color = Color.Gray,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("INSTELLINGEN", color = Color.White, fontSize = 24.sp)
        Text("Camera merk: nog niet gekozen", color = Color.Gray, fontSize = 16.sp)
        Text("Camera IP: 192.168.1.100", color = Color.Gray, fontSize = 16.sp)
        Text("Gebruiker: admin", color = Color.Gray, fontSize = 16.sp)
        Text("Protocol: ONVIF / RTSP voorbereid", color = Color.Green, fontSize = 16.sp)
        Text("Status: camera niet gekoppeld", color = Color.Red, fontSize = 16.sp)
    }
}

@Composable
fun ZoomButtons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 8.dp)
            .wrapContentSize(Alignment.CenterEnd),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("1×", "4×", "8×", "MAX").forEach { label ->
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
                modifier = Modifier
                    .width(78.dp)
                    .height(42.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    active: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("LIVE", "THERM", "AI", "MAP", "FOTO", "INSTEL")
            .forEach { item ->

                Button(
                    onClick = { onSelect(item) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (active == item) {
                                Color(0xFF1565C0)
                            } else {
                                Color.DarkGray
                            }
                    ),
                    modifier = Modifier.width(110.dp)
                ) {
                    Text(
                        text = item,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
    }
}

@Composable
fun SideMenu(
    onSelect: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(Color(0xFF202020))
            .verticalScroll(scrollState)
            .padding(12.dp)
    ) {
        Text(
            text = "MENU",
            color = Color.White,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        listOf(
            "CAMERAS" to "📷  CAMERA'S",
            "THERM" to "🌡  THERM INST.",
            "AI" to "🤖  AI INST.",
            "FOTO" to "📁  OPNAMES",
            "CLOUD" to "☁  CLOUD",
            "USERS" to "👥  GEBRUIKERS",
            "VEHICLE" to "🚒  VOERTUIG",
            "TAAL" to "🌍  TAAL",
            "OVER" to "ℹ  OVER"
        ).forEach { item ->

            Button(
                onClick = { onSelect(item.first) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(bottom = 6.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = item.second,
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }
    }
}