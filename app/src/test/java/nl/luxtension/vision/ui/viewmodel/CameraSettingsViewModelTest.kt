package nl.luxtension.vision.ui.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nl.luxtension.vision.domain.model.CameraConnectionStatus
import nl.luxtension.vision.domain.model.CameraProtocol
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CameraSettingsViewModelTest {
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @Before fun setUp() { Dispatchers.setMain(dispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }

    @Test fun validationReportsRequiredFields() {
        val errors = CameraSettingsViewModel.validateState(CameraSettingsUiState())
        assertTrue(errors.hasErrors)
        assertNotNull(errors.displayName)
        assertNotNull(errors.ipAddress)
        assertNotNull(errors.username)
        assertNotNull(errors.password)
    }

    @Test fun rtspPathRequiredOnlyForRtsp() {
        assertNull(CameraSettingsViewModel.validateState(validState(selectedProtocol = CameraProtocol.ONVIF)).rtspPath)
        assertNotNull(CameraSettingsViewModel.validateState(validState(selectedProtocol = CameraProtocol.RTSP)).rtspPath)
    }

    @Test fun viewModelTransitionsToConnectedAfterSuccessfulTest() = runTest(dispatcher) {
        val vm = CameraSettingsViewModel()
        fillValid(vm)
        vm.testConnection()
        runCurrent()
        assertEquals(CameraConnectionStatus.Testing, vm.uiState.value.connectionStatus)
        advanceUntilIdle()
        assertEquals(CameraConnectionStatus.Connected, vm.uiState.value.connectionStatus)
        assertEquals("Camera Alpha", vm.uiState.value.savedDisplayName)
    }

    @Test fun viewModelTransitionsToFailedAfterFailedTest() = runTest(dispatcher) {
        val vm = CameraSettingsViewModel()
        fillValid(vm)
        vm.onIpAddressChanged("example.com")
        vm.testConnection()
        advanceUntilIdle()
        assertEquals(CameraConnectionStatus.Failed, vm.uiState.value.connectionStatus)
    }

    @Test fun passwordIsHiddenByDefaultAndOnlyVisibleWhenToggled() {
        val vm = CameraSettingsViewModel()
        vm.onPasswordChanged("hidden-secret")
        assertFalse(vm.uiState.value.passwordVisible)
        vm.togglePasswordVisibility()
        assertTrue(vm.uiState.value.passwordVisible)
    }

    private fun fillValid(vm: CameraSettingsViewModel) {
        vm.onDisplayNameChanged("Camera Alpha")
        vm.onIpAddressChanged("10.0.0.25")
        vm.onPortChanged("80")
        vm.onUsernameChanged("operator")
        vm.onPasswordChanged("hidden-secret")
    }

    private fun validState(selectedProtocol: CameraProtocol) = CameraSettingsUiState(
        displayName = "Camera Alpha", ipAddress = "10.0.0.25", port = "80", username = "operator",
        password = "hidden-secret", selectedProtocol = selectedProtocol,
    )
}
