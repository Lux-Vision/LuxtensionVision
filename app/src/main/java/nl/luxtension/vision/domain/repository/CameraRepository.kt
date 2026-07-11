package nl.luxtension.vision.domain.repository

import nl.luxtension.vision.domain.model.CameraConfiguration

interface CameraRepository {
    suspend fun testConnection(configuration: CameraConfiguration): Result<Unit>
    suspend fun saveConfiguration(configuration: CameraConfiguration): Result<Unit>
    suspend fun loadConfiguration(): Result<CameraConfiguration?>
}
