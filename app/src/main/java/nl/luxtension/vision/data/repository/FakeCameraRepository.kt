package nl.luxtension.vision.data.repository

import kotlinx.coroutines.delay
import nl.luxtension.vision.domain.model.CameraConfiguration
import nl.luxtension.vision.domain.repository.CameraRepository

class FakeCameraRepository : CameraRepository {
    private var savedConfiguration: CameraConfiguration? = null

    override suspend fun testConnection(configuration: CameraConfiguration): Result<Unit> {
        delay(250)
        val host = configuration.ipAddress.trim().lowercase()
        val unreachable = host.isBlank() || host in blockedHosts || host.startsWith("192.0.2.") || host.startsWith("198.51.100.") || host.startsWith("203.0.113.")
        return if (configuration.enabled && !unreachable && configuration.port in 1..65535) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalStateException("Camera connection test failed in fake repository"))
        }
    }

    override suspend fun saveConfiguration(configuration: CameraConfiguration): Result<Unit> {
        // TODO: Replace in-memory storage with encrypted storage using Android Keystore,
        // EncryptedSharedPreferences, or an equivalent secure storage mechanism. Never persist
        // camera passwords in plain text.
        savedConfiguration = configuration.copy(password = "")
        return Result.success(Unit)
    }

    override suspend fun loadConfiguration(): Result<CameraConfiguration?> = Result.success(savedConfiguration)

    private companion object {
        val blockedHosts = setOf("0.0.0.0", "127.0.0.1", "localhost", "example.com", "camera.local")
    }
}
