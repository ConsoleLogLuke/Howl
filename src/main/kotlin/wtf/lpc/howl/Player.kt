package wtf.lpc.howl

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import java.time.LocalDateTime

var currentEvent: Event? = null

lateinit var hourlyMusicPlayer: MediaPlayer
lateinit var kkSliderMusicPlayer: MediaPlayer
lateinit var rainSoundsMusicPlayer: MediaPlayer
val cicadaSoundsMusicPlayers = mutableMapOf<CicadaType, MediaPlayer>()
lateinit var fireworkSoundsMusicPlayer: MediaPlayer

enum class Event {
    NEW_YEARS_DAY,
    FESTIVALE,
    BUNNY_DAY,
    FIREWORK_SHOW,
    HALLOWEEN,
    HARVEST_FESTIVAL,
    TOY_DAY,
    NEW_YEARS_EVE
}

enum class PeriodOfDay { AM, PM }
data class TwelveHour(val hour: Int, val period: PeriodOfDay) {
    fun toInternalString() = "$hour${period.toString().toLowerCase()}"
    fun toNiceString() = "$hour $period"
}

fun twentyFourToTwelve(hour: Int): TwelveHour {
    var period = PeriodOfDay.AM
    var twelveHour = hour
    if (hour >= 12) period = PeriodOfDay.PM
    if (hour >= 13) twelveHour = hour - 12
    if (hour == 0) twelveHour = 12
    return TwelveHour(twelveHour, period)
}

fun playRainSounds(weather: Weather) {
    if (settings.rainSounds == RainSounds.NO_RAIN_SOUNDS || weather != Weather.RAINY) {
        if (::rainSoundsMusicPlayer.isInitialized) rainSoundsMusicPlayer.stop()
        return
    }
    val file = getAsset("weatherSounds", "${settings.rainSounds.key}.mp3")
    val media = Media(file.toURI().toString())
    rainSoundsMusicPlayer = MediaPlayer(media)
    rainSoundsMusicPlayer.setOnEndOfMedia {
        rainSoundsMusicPlayer.seek(Duration.ZERO)
        rainSoundsMusicPlayer.play()
    }
    rainSoundsMusicPlayer.play()
}

fun playCicadaSounds(dateTime: LocalDateTime) {
    for (player in cicadaSoundsMusicPlayers) player.value.stop()
    if (settings.cicadaSounds == CicadaSounds.NO_CICADA_SOUNDS) return
    for (cicadaType in settings.cicadaTypes) {
        if (settings.cicadaSounds == CicadaSounds.TIMED && dateTime.hour !in cicadaType.hours) continue
        val file = getAsset("cicadaSounds", "${cicadaType.key}.mp3")
        val media = Media(file.toURI().toString())
        cicadaSoundsMusicPlayers[cicadaType] = MediaPlayer(media)
        cicadaSoundsMusicPlayers[cicadaType]?.setOnEndOfMedia {
            cicadaSoundsMusicPlayers[cicadaType]?.seek(Duration.ZERO)
            cicadaSoundsMusicPlayers[cicadaType]?.play()
        }
        cicadaSoundsMusicPlayers[cicadaType]?.play()
    }
}

fun playFireworkSounds() {
    if (
        settings.fireworkSounds == FireworkSounds.NO_FIREWORK_SOUNDS ||
        (settings.fireworkSounds == FireworkSounds.DURING_FIREWORK_SHOWS && currentEvent != Event.FIREWORK_SHOW)
    ) {
        if (::fireworkSoundsMusicPlayer.isInitialized) fireworkSoundsMusicPlayer.stop()
        return
    }
    val file = getAsset("fireworks.mp3")
    val media = Media(file.toURI().toString())
    fireworkSoundsMusicPlayer = MediaPlayer(media)
    fireworkSoundsMusicPlayer.setOnEndOfMedia {
        fireworkSoundsMusicPlayer.seek(Duration.ZERO)
        fireworkSoundsMusicPlayer.play()
    }
    fireworkSoundsMusicPlayer.play()
}

fun resetPlayers() {
    val dateTime = LocalDateTime.now()
    val weather = getWeather()

    playRainSounds(weather)
    playCicadaSounds(dateTime)
    playFireworkSounds()
}
