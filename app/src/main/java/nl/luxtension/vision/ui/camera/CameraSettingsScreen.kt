package nl.luxtension.vision.ui.camera

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.luxtension.vision.R
import nl.luxtension.vision.domain.model.*
import nl.luxtension.vision.ui.viewmodel.CameraSettingsUiState

@Composable
fun CameraSettingsScreen(state: CameraSettingsUiState, actions: CameraSettingsActions) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(stringResource(R.string.camera_settings_title), color = Color.White, fontSize = 24.sp)
        StatusChip(state.connectionStatus)
        Selector(stringResource(R.string.camera_brand), CameraBrand.entries.map { it.name }, state.selectedBrand.name) { actions.onBrand(CameraBrand.valueOf(it)) }
        Field(stringResource(R.string.camera_name), state.displayName, actions.onDisplayName, state.errors.displayName)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Field(stringResource(R.string.camera_host), state.ipAddress, actions.onIp, state.errors.ipAddress, Modifier.weight(1f))
            Field(stringResource(R.string.camera_port), state.port, actions.onPort, state.errors.port, Modifier.width(130.dp), KeyboardType.Number)
        }
        Field(stringResource(R.string.camera_username), state.username, actions.onUsername, state.errors.username)
        OutlinedTextField(
            value = state.password,
            onValueChange = actions.onPassword,
            label = { Text(stringResource(R.string.camera_password)) },
            isError = state.errors.password != null,
            supportingText = { state.errors.password?.let { Text(it) } },
            visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = { TextButton(onClick = actions.onTogglePassword) { Text(stringResource(if (state.passwordVisible) R.string.camera_hide else R.string.camera_show)) } },
            modifier = Modifier.fillMaxWidth(),
            colors = fieldColors(),
        )
        Selector(stringResource(R.string.camera_protocol), CameraProtocol.entries.map { it.name }, state.selectedProtocol.name) { actions.onProtocol(CameraProtocol.valueOf(it)) }
        if (state.selectedProtocol == CameraProtocol.RTSP) Field(stringResource(R.string.camera_rtsp_path), state.rtspPath, actions.onRtsp, state.errors.rtspPath)
        SwitchRow(stringResource(R.string.camera_tls), state.useTls, actions.onTls)
        SwitchRow(stringResource(R.string.camera_enabled), state.enabled, actions.onEnabled)
        state.successMessage?.let { Text(it, color = Color(0xFF4CAF50)) }
        state.errorMessage?.let { Text(it, color = Color(0xFFE53935)) }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            Button(actions.onTest, enabled = !state.isLoading, modifier = Modifier.weight(1f).height(52.dp)) { Text(stringResource(R.string.camera_test_connection)) }
            Button(actions.onSave, enabled = !state.isLoading, modifier = Modifier.weight(1f).height(52.dp)) { Text(stringResource(R.string.camera_save)) }
            Button(actions.onReset, modifier = Modifier.weight(1f).height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) { Text(stringResource(R.string.camera_clear)) }
        }
    }
}

data class CameraSettingsActions(
    val onDisplayName: (String) -> Unit,
    val onIp: (String) -> Unit,
    val onPort: (String) -> Unit,
    val onUsername: (String) -> Unit,
    val onPassword: (String) -> Unit,
    val onRtsp: (String) -> Unit,
    val onTls: (Boolean) -> Unit,
    val onEnabled: (Boolean) -> Unit,
    val onBrand: (CameraBrand) -> Unit,
    val onProtocol: (CameraProtocol) -> Unit,
    val onTogglePassword: () -> Unit,
    val onTest: () -> Unit,
    val onSave: () -> Unit,
    val onReset: () -> Unit,
)

@Composable private fun Field(label: String, value: String, onChange: (String) -> Unit, error: String?, modifier: Modifier = Modifier.fillMaxWidth(), keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(value, onChange, label = { Text(label) }, isError = error != null, supportingText = { error?.let { Text(it) } }, keyboardOptions = KeyboardOptions(keyboardType = keyboardType), modifier = modifier, colors = fieldColors())
}

@Composable private fun Selector(label: String, values: List<String>, selected: String, onSelected: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) { Text(label, color = Color.LightGray); Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { values.forEach { FilterChip(selected = it == selected, onClick = { onSelected(it) }, label = { Text(it) }) } } }
}

@Composable private fun SwitchRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) = Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, color = Color.White); Switch(checked, onChange) }

@Composable private fun StatusChip(status: CameraConnectionStatus) {
    val color = when (status) { CameraConnectionStatus.NotConfigured -> Color.Gray; CameraConnectionStatus.Testing -> Color(0xFFFFB300); CameraConnectionStatus.Connected -> Color(0xFF43A047); CameraConnectionStatus.Failed -> Color(0xFFE53935) }
    Text("● ${status.name}", color = color, fontSize = 16.sp)
}

@Composable private fun fieldColors() = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray, cursorColor = Color.White)
