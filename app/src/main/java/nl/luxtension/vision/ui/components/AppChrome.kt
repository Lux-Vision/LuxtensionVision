package nl.luxtension.vision.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.luxtension.vision.ui.foundation.LuxtensionColors
import nl.luxtension.vision.ui.foundation.LuxtensionDimens
import nl.luxtension.vision.ui.foundation.LuxtensionStrings
import nl.luxtension.vision.ui.model.Screen

@Composable
fun LuxtensionHeader(date: String, time: String, onMenuClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onMenuClick, colors = ButtonDefaults.buttonColors(containerColor = LuxtensionColors.Background), contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp)) {
                Text("☰", color = LuxtensionColors.TextPrimary, fontSize = 26.sp)
            }
            Text(LuxtensionStrings.AppTitle, color = LuxtensionColors.TextPrimary, fontSize = 22.sp)
        }
        Text("$date   $time", color = LuxtensionColors.TextPrimary, fontSize = 16.sp)
    }
}

@Composable
fun ConnectionStatusBar(modifier: Modifier = Modifier) {
    Text(LuxtensionStrings.Status, color = LuxtensionColors.Online, fontSize = 13.sp, modifier = modifier)
}

@Composable
fun BottomNavigationBar(active: Screen, onSelect: (Screen) -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(LuxtensionDimens.BottomBarSpacing, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Screen.bottomNavigation.forEach { screen ->
            Button(
                onClick = { onSelect(screen) },
                colors = ButtonDefaults.buttonColors(containerColor = if (active == screen) LuxtensionColors.Accent else LuxtensionColors.Control),
                modifier = Modifier.width(LuxtensionDimens.BottomButtonMinWidth)
            ) {
                Text(screen.navLabel, color = LuxtensionColors.TextPrimary, fontSize = 12.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun SideMenu(onSelect: (Screen) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight().width(LuxtensionDimens.SideMenuWidth).background(LuxtensionColors.Panel).verticalScroll(rememberScrollState()).padding(12.dp)
    ) {
        Text("MENU", color = LuxtensionColors.TextPrimary, fontSize = 22.sp)
        Spacer(Modifier.height(10.dp))
        val labels = mapOf(
            Screen.Cameras to "📷  CAMERA'S", Screen.Thermal to "🌡  THERM INST.", Screen.Ai to "🤖  AI INST.",
            Screen.Recordings to "📁  OPNAMES", Screen.Cloud to "☁  CLOUD", Screen.Users to "👥  GEBRUIKERS",
            Screen.Vehicle to "🚒  VOERTUIG", Screen.Language to "🌍  TAAL", Screen.About to "ℹ  OVER"
        )
        Screen.sideMenu.forEach { screen ->
            Button(onClick = { onSelect(screen) }, colors = ButtonDefaults.buttonColors(containerColor = LuxtensionColors.Control), modifier = Modifier.fillMaxWidth().height(44.dp).padding(bottom = 6.dp), contentPadding = PaddingValues(0.dp)) {
                Text(labels.getValue(screen), color = LuxtensionColors.TextPrimary, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun PlaceholderContent(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(title, color = LuxtensionColors.TextPrimary, fontSize = 26.sp)
        Spacer(Modifier.height(12.dp))
        Text(subtitle, color = LuxtensionColors.TextSecondary, fontSize = 16.sp)
    }
}
