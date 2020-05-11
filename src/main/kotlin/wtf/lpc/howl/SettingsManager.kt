package wtf.lpc.howl

import org.json.JSONObject
import java.io.File
import javax.swing.JRadioButton

lateinit var settings: Settings

val settingsFile = File(dataDir, "settings.json")

enum class Game(val key: String) {
    ANIMAL_CROSSING("animalCrossing"),
    WILD_WORLD("wildWorld"),
    NEW_LEAF("newLeaf"),
    NEW_HORIZONS("newHorizons");

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class Weather(val key: String, val radioButton: JRadioButton) {
    AUTOMATIC("automatic", autoWeatherButton),
    SUNNY("sunny", sunnyWeatherButton),
    RAINY("rainy", rainyWeatherButton),
    SNOWY("snowy", snowyWeatherButton);

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class KkSlider(val key: String) {
    KK_SLIDER("kkSlider"),
    DJ_KK("djKk");

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class KkSongType(val key: String) {
    AIRCHECK("aircheck"),
    LIVE("live");

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class RainSounds(val key: String, val radioButton: JRadioButton) {
    OUTDOOR_RAIN("outdoorRain", outdoorRainButton),
    INDOOR_RAIN("indoorRain", indoorRainButton),
    OUTDOOR_THUNDER("outdoorThunder", outdoorThunderButton),
    INDOOR_THUNDER("indoorThunder", indoorThunderButton),
    NO_RAIN_SOUNDS("noRainSounds", noRainSoundsButton);

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class CicadaSounds(val key: String, val radioButton: JRadioButton) {
    TIMED("timed", timedCicadaSoundsButton),
    ALWAYS("always", alwaysCicadaSoundsButton),
    NO_CICADA_SOUNDS("noCicadaSounds", noCicadaSoundsButton);

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class CicadaType(val key: String) {
    BROWN("brown"),
    EVENING("evening"),
    GIANT("giant"),
    ROBUST("robust"),
    WALKER("walker");

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

enum class FireworkSounds(val key: String, val radioButton: JRadioButton) {
    DURING_FIREWORK_SHOWS("duringFireworkShows", duringFireworkShowsButton),
    ALWAYS("always", alwaysFireworksButton),
    NO_FIREWORK_SOUNDS("noFireworkSounds", noFireworkSoundsButton);

    companion object {
        fun fromKey(key: String) = values().first { it.key == key }
    }
}

data class Settings(
    var games: List<Game>,
    var weather: Weather,
    var kkSlider: List<KkSlider>,
    var kkSongTypes: List<KkSongType>,
    var rainSounds: RainSounds,
    var cicadaSounds: CicadaSounds,
    var cicadaTypes: List<CicadaType>,
    var fireworkSounds: FireworkSounds,
    var grandfatherMode: Boolean,
    var eventSongs: Boolean
)

fun loadSettings() {
    if (!settingsFile.exists()) {
        settingsFile.createNewFile()
        val defaultSettings = mapOf(
            "games" to listOf("newHorizons"),
            "weather" to "automatic",
            "kkSlider" to listOf("kkSlider"),
            "kkSongTypes" to listOf("live"),
            "rainSounds" to "outdoorRain",
            "cicadaSounds" to "noCicadaSounds",
            "cicadaTypes" to listOf("brown", "evening", "giant", "robust", "walker"),
            "fireworkSounds" to "duringFireworkShows",
            "grandfatherMode" to false,
            "eventSongs" to true
        )
        val jsonString = JSONObject(defaultSettings).toString()
        settingsFile.writeText(jsonString)
    }

    val jsonObject = JSONObject(settingsFile.readText())
    val games = jsonObject.getJSONArray("games").map { Game.fromKey(it.toString()) }
    val weather = Weather.fromKey(jsonObject.getString("weather"))
    val kkSlider = jsonObject.getJSONArray("kkSlider").map { KkSlider.fromKey(it.toString()) }
    val kkSongTypes = jsonObject.getJSONArray("kkSongTypes").map { KkSongType.fromKey(it.toString()) }
    val rainSounds = RainSounds.fromKey(jsonObject.getString("rainSounds"))
    val cicadaSounds = CicadaSounds.fromKey(jsonObject.getString("cicadaSounds"))
    val cicadaTypes = jsonObject.getJSONArray("cicadaTypes").map { CicadaType.fromKey(it.toString()) }
    val fireworkSounds = FireworkSounds.fromKey(jsonObject.getString("fireworkSounds"))
    val grandfatherMode = jsonObject.getBoolean("grandfatherMode")
    val eventSongs = jsonObject.getBoolean("eventSongs")

    settings = Settings(
        games,
        weather,
        kkSlider,
        kkSongTypes,
        rainSounds,
        cicadaSounds,
        cicadaTypes,
        fireworkSounds,
        grandfatherMode,
        eventSongs
    )

    animalCrossingCheckBox.isSelected = Game.ANIMAL_CROSSING in settings.games
    wildWorldCheckBox.isSelected = Game.WILD_WORLD in settings.games
    newLeafCheckBox.isSelected = Game.NEW_LEAF in settings.games
    newHorizonsCheckBox.isSelected = Game.NEW_HORIZONS in settings.games

    weatherButtonGroup.clearSelection()
    settings.weather.radioButton.isSelected = true

    kkSliderCheckBox.isSelected = KkSlider.KK_SLIDER in settings.kkSlider
    djKkCheckBox.isSelected = KkSlider.DJ_KK in settings.kkSlider

    aircheckCheckBox.isSelected = KkSongType.AIRCHECK in settings.kkSongTypes
    liveCheckBox.isSelected = KkSongType.LIVE in settings.kkSongTypes

    rainSoundsButtonGroup.clearSelection()
    settings.rainSounds.radioButton.isSelected = true

    cicadaSoundsButtonGroup.clearSelection()
    settings.cicadaSounds.radioButton.isSelected = true

    brownCicadaCheckBox.isSelected = CicadaType.BROWN in settings.cicadaTypes
    eveningCicadaCheckBox.isSelected = CicadaType.EVENING in settings.cicadaTypes
    giantCicadaCheckBox.isSelected = CicadaType.GIANT in settings.cicadaTypes
    robustCicadaCheckBox.isSelected = CicadaType.ROBUST in settings.cicadaTypes
    walkerCicadaCheckBox.isSelected = CicadaType.WALKER in settings.cicadaTypes

    fireworkSoundsButtonGroup.clearSelection()
    settings.fireworkSounds.radioButton.isSelected = true

    grandfatherModeCheckBox.isSelected = settings.grandfatherMode
    eventSongsCheckBox.isSelected = settings.eventSongs
}

fun saveSettings() {
    val games = mutableListOf<Game>()
    if (animalCrossingCheckBox.isSelected) games.add(Game.ANIMAL_CROSSING)
    if (wildWorldCheckBox.isSelected) games.add(Game.WILD_WORLD)
    if (newLeafCheckBox.isSelected) games.add(Game.NEW_LEAF)
    if (newHorizonsCheckBox.isSelected) games.add(Game.NEW_HORIZONS)
    settings.games = games

    settings.weather = when {
        autoWeatherButton.isSelected -> Weather.AUTOMATIC
        sunnyWeatherButton.isSelected -> Weather.SUNNY
        rainyWeatherButton.isSelected -> Weather.RAINY
        snowyWeatherButton.isSelected -> Weather.SNOWY
        else -> Weather.AUTOMATIC
    }

    val kkSlider = mutableListOf<KkSlider>()
    if (kkSliderCheckBox.isSelected) kkSlider.add(KkSlider.KK_SLIDER)
    if (djKkCheckBox.isSelected) kkSlider.add(KkSlider.DJ_KK)
    settings.kkSlider = kkSlider

    val kkSongTypes = mutableListOf<KkSongType>()
    if (aircheckCheckBox.isSelected) kkSongTypes.add(KkSongType.AIRCHECK)
    if (liveCheckBox.isSelected) kkSongTypes.add(KkSongType.LIVE)
    settings.kkSongTypes = kkSongTypes

    settings.rainSounds = when {
        outdoorRainButton.isSelected -> RainSounds.OUTDOOR_RAIN
        indoorRainButton.isSelected -> RainSounds.INDOOR_RAIN
        outdoorThunderButton.isSelected -> RainSounds.OUTDOOR_THUNDER
        indoorThunderButton.isSelected -> RainSounds.INDOOR_THUNDER
        noRainSoundsButton.isSelected -> RainSounds.NO_RAIN_SOUNDS
        else -> RainSounds.OUTDOOR_RAIN
    }

    settings.cicadaSounds = when {
        timedCicadaSoundsButton.isSelected -> CicadaSounds.TIMED
        alwaysCicadaSoundsButton.isSelected -> CicadaSounds.ALWAYS
        noCicadaSoundsButton.isSelected -> CicadaSounds.NO_CICADA_SOUNDS
        else -> CicadaSounds.TIMED
    }

    val cicadaTypes = mutableListOf<CicadaType>()
    if (brownCicadaCheckBox.isSelected) cicadaTypes.add(CicadaType.BROWN)
    if (eveningCicadaCheckBox.isSelected) cicadaTypes.add(CicadaType.EVENING)
    if (giantCicadaCheckBox.isSelected) cicadaTypes.add(CicadaType.GIANT)
    if (robustCicadaCheckBox.isSelected) cicadaTypes.add(CicadaType.ROBUST)
    if (walkerCicadaCheckBox.isSelected) cicadaTypes.add(CicadaType.WALKER)
    settings.cicadaTypes = cicadaTypes

    settings.fireworkSounds = when {
        duringFireworkShowsButton.isSelected -> FireworkSounds.DURING_FIREWORK_SHOWS
        alwaysFireworksButton.isSelected -> FireworkSounds.ALWAYS
        noFireworkSoundsButton.isSelected -> FireworkSounds.NO_FIREWORK_SOUNDS
        else -> FireworkSounds.DURING_FIREWORK_SHOWS
    }

    settings.grandfatherMode = grandfatherModeCheckBox.isSelected
    settings.eventSongs = eventSongsCheckBox.isSelected

    val newSettings = mapOf(
        "games" to settings.games.map { it.key },
        "weather" to settings.weather.key,
        "kkSlider" to settings.kkSlider.map { it.key },
        "kkSongTypes" to settings.kkSongTypes.map { it.key },
        "rainSounds" to settings.rainSounds.key,
        "cicadaSounds" to settings.cicadaSounds.key,
        "cicadaTypes" to settings.cicadaTypes.map { it.key },
        "fireworkSounds" to settings.fireworkSounds,
        "grandfatherMode" to settings.grandfatherMode,
        "eventSongs" to settings.eventSongs
    )

    val jsonString = JSONObject(newSettings).toString()
    settingsFile.writeText(jsonString)
}
