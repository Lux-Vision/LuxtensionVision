package nl.luxtension.vision.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import nl.luxtension.vision.ui.components.BottomNavigationBar
import nl.luxtension.vision.ui.components.ConnectionStatusBar
import nl.luxtension.vision.ui.components.LuxtensionHeader
import nl.luxtension.vision.ui.components.PlaceholderContent
import nl.luxtension.vision.ui.components.SideMenu
import nl.luxtension.vision.ui.foundation.LuxtensionColors
import nl.luxtension.vision.ui.foundation.LuxtensionDimens
import nl.luxtension.vision.ui.foundation.LuxtensionStrings
import nl.luxtension.vision.ui.model.Screen
import nl.luxtension.vision.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LiveViewScreen(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var clock by remember { mutableStateOf(ClockText()) }

    LaunchedEffect(Unit) {
        while (true) {
            clock = ClockText()
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(LuxtensionColors.Background).windowInsetsPadding(WindowInsets.safeDrawing)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(
                top = 12.dp,
                start = LuxtensionDimens.OuterPadding,
                end = 72.dp,
                bottom = 12.dp
            )
        ) {
            LuxtensionHeader(date = clock.date, time = clock.time, onMenuClick = viewModel::toggleSideMenu)
            ConnectionStatusBar()
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth().background(LuxtensionColors.Surface),
                contentAlignment = Alignment.Center
            ) {
                ScreenContent(screen = uiState.selectedScreen)
            }
            Spacer(Modifier.height(8.dp))
            BottomNavigationBar(active = uiState.selectedScreen, onSelect = viewModel::selectScreen)
        }

        if (uiState.isSideMenuOpen) {
            SideMenu(onSelect = viewModel::selectScreen)
        }
    }
}

@Composable
private fun ScreenContent(screen: Screen) {
    when (screen) {
        Screen.Live -> LiveContent()
        Screen.Settings -> SettingsContent()
        else -> PlaceholderContent(screen.title, screen.subtitle)
    }
}

@Composable
private fun LiveContent() {
    Text(LuxtensionStrings.LiveWaiting, color = LuxtensionColors.TextSecondary, fontSize = 28.sp)
    Text(
        LuxtensionStrings.LiveInfo,
        color = LuxtensionColors.TextSecondary,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxSize().padding(12.dp).wrapContentSize(Alignment.BottomStart)
    )
    ZoomButtons()
}

@Composable
private fun SettingsContent() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(Screen.Settings.title, color = LuxtensionColors.TextPrimary, fontSize = 24.sp)
        Text("Camera merk: nog niet gekozen", color = LuxtensionColors.TextSecondary, fontSize = 16.sp)
        Text("Camera IP: niet ingesteld", color = LuxtensionColors.TextSecondary, fontSize = 16.sp)
        Text("Gebruiker: niet ingesteld", color = LuxtensionColors.TextSecondary, fontSize = 16.sp)
        Text("Protocol: ONVIF / RTSP voorbereid", color = LuxtensionColors.Online, fontSize = 16.sp)
        Text("Status: camera niet gekoppeld", color = LuxtensionColors.Offline, fontSize = 16.sp)
    }
}

@Composable
private fun ZoomButtons() {
    Column(modifier = Modifier.fillMaxSize().padding(end = 8.dp).wrapContentSize(Alignment.CenterEnd), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("1×", "4×", "8×", "MAX").forEach { label ->
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = LuxtensionColors.Control),
                modifier = Modifier.width(LuxtensionDimens.ZoomButtonWidth).height(LuxtensionDimens.ZoomButtonHeight),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(label, color = LuxtensionColors.TextPrimary, fontSize = 14.sp)
            }
        }
    }
}

private data class ClockText(val date: String = currentDate(), val time: String = currentTime())

private fun currentDate(): String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
private fun currentTime(): String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
