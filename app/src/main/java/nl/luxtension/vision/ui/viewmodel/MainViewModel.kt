package nl.luxtension.vision.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.luxtension.vision.ui.model.Screen

data class MainUiState(
    val selectedScreen: Screen = Screen.Live,
    val isSideMenuOpen: Boolean = false
)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun toggleSideMenu() = _uiState.update { it.copy(isSideMenuOpen = !it.isSideMenuOpen) }
    fun selectScreen(screen: Screen) = _uiState.update { it.copy(selectedScreen = screen, isSideMenuOpen = false) }
}
