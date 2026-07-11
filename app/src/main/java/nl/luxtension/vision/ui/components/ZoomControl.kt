package nl.luxtension.vision.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun ZoomControl(
    modifier: Modifier = Modifier,
    onZoomSelected: (String) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        listOf("+", "−", "1x", "4x", "8x", "MAX").forEach { label ->
            Button(
                onClick = { onZoomSelected(label) },
                modifier = Modifier
                    .width(72.dp)
                    .height(46.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (label == "1x") LuxtensionBlue else LuxtensionPanel,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
