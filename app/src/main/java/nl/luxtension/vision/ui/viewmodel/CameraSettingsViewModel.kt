package nl.luxtension.vision.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.luxtension.vision.data.repository.FakeCameraRepository
import nl.luxtension.vision.domain.model.CameraBrand
import nl.luxtension.vision.domain.model.CameraConfiguration
import nl.luxtension.vision.domain.model.CameraConnectionStatus
import nl.luxtension.vision.domain.model.CameraProtocol
import nl.luxtension.vision.domain.repository.CameraRepository
import java.util.UUID

private val HostRegex = Regex("^(?=.{1,253}$)(?!-)[A-Za-z0-9-]{1,63}(?<!-)(\\.(?!-)[A-Za-z0-9-]{1,63}(?<!-))*$|^((25[0-5]|2[0-4]\\d|1?\\d?\\d)(\\.|$)){4}$")

data class CameraFormErrors(
    val displayName: String? = null,
    val ipAddress: String? = null,
    val port: String? = null,
    val username: String? = null,
    val password: String? = null,
    val rtspPath: String? = null,
) { val hasErrors: Boolean get() = listOf(displayName, ipAddress, port, username, password, rtspPath).any { it != null } }

data class CameraSettingsUiState(
    val id: String = UUID.randomUUID().toString(),
    val displayName: String = "",
    val ipAddress: String = "",
    val port: String = "80",
    val username: String = "",
    val password: String = "",
    val rtspPath: String = "",
    val useTls: Boolean = false,
    val enabled: Boolean = true,
    val selectedBrand: CameraBrand = CameraBrand.ONVIF,
    val selectedProtocol: CameraProtocol = CameraProtocol.ONVIF,
    val passwordVisible: Boolean = false,
    val connectionStatus: CameraConnectionStatus = CameraConnectionStatus.NotConfigured,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val errors: CameraFormErrors = CameraFormErrors(),
    val savedDisplayName: String? = null,
)

class CameraSettingsViewModel(
    private val repository: CameraRepository = FakeCameraRepository(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(CameraSettingsUiState())
    val uiState: StateFlow<CameraSettingsUiState> = _uiState.asStateFlow()

    fun onDisplayNameChanged(value: String) = patch { copy(displayName = value) }
    fun onIpAddressChanged(value: String) = patch { copy(ipAddress = value) }
    fun onPortChanged(value: String) = patch { copy(port = value.filter(Char::isDigit).take(5)) }
    fun onUsernameChanged(value: String) = patch { copy(username = value) }
    fun onPasswordChanged(value: String) = patch { copy(password = value) }
    fun onRtspPathChanged(value: String) = patch { copy(rtspPath = value) }
    fun onUseTlsChanged(value: Boolean) = patch { copy(useTls = value) }
    fun onEnabledChanged(value: Boolean) = patch { copy(enabled = value) }
    fun togglePasswordVisibility() = patch { copy(passwordVisible = !passwordVisible) }
    fun selectBrand(value: CameraBrand) = patch { copy(selectedBrand = value) }
    fun selectProtocol(value: CameraProtocol) = patch { copy(selectedProtocol = value) }

    fun testConnection() = submit(testOnly = true)
    fun saveConfiguration() = submit(testOnly = false)

    fun resetForm() { _uiState.value = CameraSettingsUiState() }

    fun validate(): Boolean {
        val state = _uiState.value
        val errors = validateState(state)
        _uiState.update { it.copy(errors = errors, errorMessage = if (errors.hasErrors) "Controleer de gemarkeerde velden" else null) }
        return !errors.hasErrors
    }

    private fun submit(testOnly: Boolean) {
        if (!validate()) return
        val configuration = _uiState.value.toConfiguration()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, connectionStatus = CameraConnectionStatus.Testing, successMessage = null, errorMessage = null) }
            val testResult = repository.testConnection(configuration)
            if (testResult.isFailure) {
                _uiState.update { it.copy(isLoading = false, connectionStatus = CameraConnectionStatus.Failed, errorMessage = "Verbinding mislukt") }
                return@launch
            }
            if (testOnly) {
                _uiState.update { it.copy(isLoading = false, connectionStatus = CameraConnectionStatus.Connected, savedDisplayName = configuration.displayName, successMessage = "Verbinding geslaagd") }
            } else {
                val saveResult = repository.saveConfiguration(configuration)
                _uiState.update {
                    if (saveResult.isSuccess) it.copy(isLoading = false, connectionStatus = CameraConnectionStatus.Connected, savedDisplayName = configuration.displayName, successMessage = "Camera-instellingen opgeslagen")
                    else it.copy(isLoading = false, connectionStatus = CameraConnectionStatus.Failed, errorMessage = "Opslaan mislukt")
                }
            }
        }
    }

    private fun patch(reducer: CameraSettingsUiState.() -> CameraSettingsUiState) = _uiState.update { it.reducer().copy(successMessage = null, errorMessage = null) }

    private fun CameraSettingsUiState.toConfiguration() = CameraConfiguration(id, displayName.trim(), selectedBrand, ipAddress.trim(), port.toInt(), username.trim(), password, selectedProtocol, rtspPath.trim(), useTls, enabled)

    companion object {
        fun validateState(state: CameraSettingsUiState): CameraFormErrors = CameraFormErrors(
            displayName = if (state.displayName.isBlank()) "Naam is verplicht" else null,
            ipAddress = if (!HostRegex.matches(state.ipAddress.trim())) "Voer een geldig IP-adres of hostnaam in" else null,
            port = state.port.toIntOrNull()?.takeIf { it in 1..65535 }?.let { null } ?: "Poort moet tussen 1 en 65535 liggen",
            username = if (state.username.isBlank()) "Gebruikersnaam is verplicht" else null,
            password = if (state.password.isBlank()) "Wachtwoord is verplicht" else null,
            rtspPath = if (state.selectedProtocol == CameraProtocol.RTSP && state.rtspPath.isBlank()) "RTSP-pad is verplicht" else null,
        )
    }
}
