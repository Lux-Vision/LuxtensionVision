package nl.luxtension.vision.domain.model

enum class CameraBrand { Hikvision, Dahua, Axis, ONVIF, Other }

enum class CameraProtocol { ONVIF, RTSP, VendorSdk }

enum class CameraConnectionStatus { NotConfigured, Testing, Connected, Failed }

data class CameraConfiguration(
    val id: String,
    val displayName: String,
    val brand: CameraBrand,
    val ipAddress: String,
    val port: Int,
    val username: String,
    val password: String,
    val protocol: CameraProtocol,
    val rtspPath: String,
    val useTls: Boolean,
    val enabled: Boolean,
)
