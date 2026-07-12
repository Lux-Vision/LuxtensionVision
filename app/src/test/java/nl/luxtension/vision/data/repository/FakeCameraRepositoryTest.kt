package nl.luxtension.vision.data.repository

import kotlinx.coroutines.test.runTest
import nl.luxtension.vision.domain.model.CameraBrand
import nl.luxtension.vision.domain.model.CameraConfiguration
import nl.luxtension.vision.domain.model.CameraProtocol
import org.junit.Assert.*
import org.junit.Test

class FakeCameraRepositoryTest {
    private val repository = FakeCameraRepository()

    @Test fun successfulFakeConnectionTest() = runTest {
        assertTrue(repository.testConnection(validConfig()).isSuccess)
    }

    @Test fun failedFakeConnectionTest() = runTest {
        assertTrue(repository.testConnection(validConfig(ipAddress = "example.com")).isFailure)
    }

    @Test fun savedConfigurationDoesNotRetainPlainTextPassword() = runTest {
        repository.saveConfiguration(validConfig(password = "top-secret"))
        val saved = repository.loadConfiguration().getOrNull()
        assertNotEquals("top-secret", saved?.password)
    }

    private fun validConfig(ipAddress: String = "10.0.0.25", password: String = "secret") = CameraConfiguration(
        id = "test", displayName = "Test Camera", brand = CameraBrand.ONVIF, ipAddress = ipAddress,
        port = 80, username = "operator", password = password, protocol = CameraProtocol.ONVIF,
        rtspPath = "", useTls = false, enabled = true,
    )
}
