package nl.luxtension.vision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import nl.luxtension.vision.ui.screens.LiveViewScreen
import nl.luxtension.vision.ui.theme.LuxtensionVisionTheme

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
