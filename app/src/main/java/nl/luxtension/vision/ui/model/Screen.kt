package nl.luxtension.vision.ui.model

sealed class Screen(
    val navLabel: String,
    val title: String,
    val subtitle: String
) {
    data object Live : Screen("LIVE", "LIVE CAMERABEELD", "Wacht op camerabeeld")
    data object Thermal : Screen("THERM", "THERMISCH BEELD", "Warmtebron detectie voorbereid")
    data object Ai : Screen("AI", "AI DETECTIE", "Personen en voertuigen detectie voorbereid")
    data object Map : Screen("MAP", "VOERTUIG LOCATIE", "GPS kaart voorbereid")
    data object Recordings : Screen("FOTO", "FOTO'S EN OPNAMES", "Snapshots en video-opnames voorbereid")
    data object Settings : Screen("INSTEL", "INSTELLINGEN", "Camera en systeeminstellingen voorbereid")
    data object Cameras : Screen("CAMERAS", "CAMERA'S", "Camera 1, Camera 2 en ONVIF voorbereid")
    data object Cloud : Screen("CLOUD", "CLOUD", "Cloud opslag voorbereid")
    data object Users : Screen("USERS", "GEBRUIKERS", "Beheerder, gebruiker en kijker voorbereid")
    data object Vehicle : Screen("VEHICLE", "VOERTUIG", "Mast, GPS en voertuigdata voorbereid")
    data object Language : Screen("TAAL", "TAAL", "Nederlands, Engels en Duits voorbereid")
    data object About : Screen("OVER", "OVER", "Luxtension Vision versie 0.2 foundation")

    companion object {
        // Use computed properties instead of statically initialized lists. This avoids
        // capturing null singleton instances during JVM class initialization.
        val bottomNavigation: List<Screen>
            get() = listOf(Live, Thermal, Ai, Map, Recordings, Settings)

        val sideMenu: List<Screen>
            get() = listOf(Cameras, Thermal, Ai, Recordings, Cloud, Users, Vehicle, Language, About)
    }
}
