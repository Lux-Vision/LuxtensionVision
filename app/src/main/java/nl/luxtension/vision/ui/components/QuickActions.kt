package nl.luxtension.vision.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.luxtension.vision.ui.theme.LuxtensionBlue
import nl.luxtension.vision.ui.theme.LuxtensionPanel

@Composable
fun QuickActions(
    modifier: Modifier = Modifier,
    onActionSelected: (String) -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listOf("Snapshot", "Record", "Fullscreen", "Thermal", "AI").forEach { action ->
            Button(
                onClick = { onActionSelected(action) },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .widthIn(min = 92.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (action == "Record") LuxtensionBlue else LuxtensionPanel,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = action.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}
