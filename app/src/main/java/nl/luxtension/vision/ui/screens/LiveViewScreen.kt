package nl.luxtension.vision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import nl.luxtension.vision.ui.components.CameraViewport
import nl.luxtension.vision.ui.components.ConnectionStatus
import nl.luxtension.vision.ui.components.QuickActions
import nl.luxtension.vision.ui.components.ZoomControl
import nl.luxtension.vision.ui.theme.LuxtensionBlue
import nl.luxtension.vision.ui.theme.LuxtensionDark
import nl.luxtension.vision.ui.theme.LuxtensionPanel
import nl.luxtension.vision.ui.theme.LuxtensionSurface
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
            currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxtensionDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 72.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Header(date = currentDate, time = currentTime, onMenuClick = { menuOpen = !menuOpen })

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(LuxtensionSurface),
                contentAlignment = Alignment.Center
            ) {
                when (activeScreen) {
                    "LIVE" -> LiveContent()
                    "THERM" -> SimpleScreen("THERMISCH BEELD", "Warmtebron detectie voorbereid")
                    "AI" -> SimpleScreen("AI DETECTIE", "Personen en voertuigen detectie voorbereid")
                    "MAP" -> SimpleScreen("VOERTUIG LOCATIE", "GPS kaart voorbereid")
                    "FOTO" -> SimpleScreen("FOTO'S EN OPNAMES", "Snapshots en video-opnames voorbereid")
                    "INSTEL" -> SettingsScreen()
                    "CAMERAS" -> SimpleScreen("CAMERA'S", "Camera configuratie voorbereid")
                    "CLOUD" -> SimpleScreen("CLOUD", "Cloud opslag voorbereid")
                    "USERS" -> SimpleScreen("GEBRUIKERS", "Beheerder, gebruiker en kijker voorbereid")
                    "VEHICLE" -> SimpleScreen("VOERTUIG", "Mast, GPS en voertuigdata voorbereid")
                    "TAAL" -> SimpleScreen("TAAL", "Nederlands, Engels en Duits voorbereid")
                    "OVER" -> SimpleScreen("OVER", "Luxtension Vision versie 0.3")
                }
            }

            BottomBar(active = activeScreen, onSelect = { activeScreen = it })
        }

        if (menuOpen) {
            SideMenu(onSelect = { activeScreen = it; menuOpen = false })
        }
    }
}

@Composable
fun Header(date: String, time: String, onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LuxtensionPanel)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onMenuClick,
                colors = ButtonDefaults.buttonColors(containerColor = LuxtensionDark),
                contentPadding = PaddingValues(horizontal = 14.dp)
            ) {
                Text(text = "☰", color = Color.White, fontSize = 24.sp)
            }

            Text(
                text = "LUXTENSION VISION",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 14.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = date, color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Text(text = time, color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            ConnectionStatus(isConnected = false)
        }
    }
}

@Composable
fun LiveContent() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val isWide = maxWidth > 900.dp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = if (isWide) 92.dp else 82.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CameraViewport(modifier = Modifier.widthIn(max = 1180.dp))
            Spacer(modifier = Modifier.height(14.dp))
            QuickActions(modifier = Modifier.widthIn(max = 900.dp))
        }

        ZoomControl(
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun SimpleScreen(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, color = Color.White, fontSize = 26.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = subtitle, color = Color.Gray, fontSize = 16.sp)
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
        Text("Camera adres: nog niet geconfigureerd", color = Color.Gray, fontSize = 16.sp)
        Text("Gebruiker: nog niet geconfigureerd", color = Color.Gray, fontSize = 16.sp)
        Text("Protocol: ONVIF / RTSP voorbereid", color = Color.Green, fontSize = 16.sp)
        Text("Status: camera niet gekoppeld", color = Color.Red, fontSize = 16.sp)
    }
}

@Composable
fun BottomBar(active: String, onSelect: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        listOf("LIVE", "THERM", "AI", "MAP", "FOTO", "INSTEL").forEach { item ->
            Button(
                onClick = { onSelect(item) },
                colors = ButtonDefaults.buttonColors(containerColor = if (active == item) LuxtensionBlue else LuxtensionPanel),
                modifier = Modifier.width(110.dp)
            ) {
                Text(text = item, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SideMenu(onSelect: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(LuxtensionPanel)
            .verticalScroll(scrollState)
            .padding(12.dp)
    ) {
        Text(text = "MENU", color = Color.White, fontSize = 22.sp)
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
                colors = ButtonDefaults.buttonColors(containerColor = LuxtensionDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .padding(bottom = 6.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = item.second, color = Color.White, fontSize = 13.sp)
            }
        }
    }
}
