package wtf.lpc.howl

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.util.Duration
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.TemporalAdjusters

var paused = false
var currentEvent: Event? = null

var hourlyMusicPlayer: MediaPlayer? = null
var rainSoundsMusicPlayer: MediaPlayer? = null
var cicadaSoundsMusicPlayers = mutableMapOf<CicadaType, MediaPlayer>()
var fireworkSoundsMusicPlayer: MediaPlayer? = null

data class DayOfYear(val month: Month, val day: Int) {
    companion object {
        fun today() = fromLocalDate(LocalDate.now())

        fun fromLocalDate(localDate: LocalDate) = DayOfYear(localDate.month, localDate.dayOfMonth)

        fun fromDayOfWeek(ordinal: Int, day: DayOfWeek, month: Month) : DayOfYear? {
            val startLocalDate = LocalDate.of(LocalDate.now().year, month, 1)
            val localDate = startLocalDate.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, day))
            if (localDate.month != month) return null
            return fromLocalDate(localDate)
        }
    }
}

fun calculateEaster() : LocalDate {
    val year = LocalDate.now().year
    val a = year % 19
    val b = year / 100
    val c = year % 100
    val d = b / 4
    val e = b % 4
    val f = (b + 8) / 25
    val g = (b - f + 1) / 3
    val h = (19 * a + b - d - g + 15) % 30
    val i = c / 4
    val k = c % 4
    val l = (32 + 2 * e + 2 * i - h - k) % 7
    val m = (a + 11 * h + 22 * l) / 451
    val month = (h + l - 7 * m + 114) / 31
    val day = (h + l - 7 * m + 114) % 31 + 1
    return LocalDate.of(year, month, day)
}

fun getNewYearSong(dateTime: LocalDateTime) : String {
    return if (dateTime.month == Month.DECEMBER) {
        when {
            dateTime.minute >= 55 -> "2355"
            dateTime.minute >= 50 -> "2350"
            dateTime.minute >= 30 -> "2330"
            else -> "2300"
        }
    } else {
        when {
            dateTime.hour >= 6 -> "0600"
            dateTime.hour >= 2 -> "0200"
            else -> "0000"
        }
    }
}

enum class Event(val key: String, val dates: List<DayOfYear?>, val times: IntRange) {
    NEW_YEARS_DAY(
        "newYears${File.separator}day",
        listOf(DayOfYear(Month.JANUARY, 1)),
        0 until 24
    ),
    FESTIVALE(
        "festivale",
        listOf(DayOfYear.fromLocalDate(calculateEaster().minusDays(48))),
        0 until 24
    ),
    BUNNY_DAY(
        "bunnyDay",
        listOf(DayOfYear.fromLocalDate(calculateEaster())),
        0 until 24
    ),
    FIREWORK_SHOW(
        "fireworksShow",
        listOf(
            DayOfYear.fromDayOfWeek(1, DayOfWeek.SUNDAY, Month.AUGUST),
            DayOfYear.fromDayOfWeek(2, DayOfWeek.SUNDAY, Month.AUGUST),
            DayOfYear.fromDayOfWeek(3, DayOfWeek.SUNDAY, Month.AUGUST),
            DayOfYear.fromDayOfWeek(4, DayOfWeek.SUNDAY, Month.AUGUST),
            DayOfYear.fromDayOfWeek(5, DayOfWeek.SUNDAY, Month.AUGUST)
        ),
        19 until 24
    ),
    HALLOWEEN(
        "halloween",
        listOf(DayOfYear(Month.OCTOBER, 31)),
        18 until 24
    ),
    HARVEST_FESTIVAL(
        "harvestFestival",
        listOf(DayOfYear.fromDayOfWeek(4, DayOfWeek.THURSDAY, Month.NOVEMBER)),
        0 until 24
    ),
    TOY_DAY(
        "toyDay",
        listOf(DayOfYear(Month.DECEMBER, 24)),
        18 until 24
    ),
    NEW_YEARS_EVE(
        "newYears${File.separator}eve",
        listOf(DayOfYear(Month.DECEMBER, 31)),
        23 until 24
    )
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

fun playHourlyMusic(dateTime: LocalDateTime, weather: Weather) {
    lateinit var file: File

    // TODO Check for KK

    var event: Event? = null
    if (settings.eventSongs) {
        val dayOfYear = DayOfYear.today()
        eventTypes@ for (eventType in Event.values()) {
            if (dateTime.hour !in eventType.times) continue
            for (date in eventType.dates) {
                if (date == dayOfYear) {
                    event = eventType
                    break@eventTypes
                }
            }
        }
    }

    if (event == null) {
        val game = settings.games.random()
        val weatherString = weather.toString().toLowerCase()
        val timeString = twentyFourToTwelve(dateTime.hour).toInternalString()

        file = getAsset("hourlyMusic", game.key, weatherString, "$timeString.mp3")
        gameLabel.text = game.initials
        gameLabel.icon = null
    } else {
        currentEvent = event

        val isNewYear = event in listOf(Event.NEW_YEARS_DAY, Event.NEW_YEARS_EVE)
        file = if (isNewYear) {
            val newYearSong = getNewYearSong(dateTime)
            getAsset("eventMusic", event.key, "${newYearSong}.mp3")
        } else getAsset("eventMusic", "${event.key}.mp3")

        val imageIcon = getIcon(
            "events${File.separator}${if (isNewYear) "newYears" else event.key}.png",
            24,
            24
        )
        gameLabel.icon = imageIcon
        gameLabel.text = null
    }

    val media = Media(file.toURI().toString())
    hourlyMusicPlayer = MediaPlayer(media)
    // TODO Much better implementation for grandfather mode
    if (!settings.grandfatherMode) {
        hourlyMusicPlayer?.setOnEndOfMedia {
            hourlyMusicPlayer?.seek(Duration.ZERO)
            hourlyMusicPlayer?.play()
        }
    }
    hourlyMusicPlayer?.play()
}

fun playRainSounds(weather: Weather) {
    if (settings.rainSounds == RainSounds.NO_RAIN_SOUNDS || weather != Weather.RAINY) return
    val file = getAsset("weatherSounds", "${settings.rainSounds.key}.mp3")
    val media = Media(file.toURI().toString())
    rainSoundsMusicPlayer = MediaPlayer(media)
    rainSoundsMusicPlayer?.setOnEndOfMedia {
        rainSoundsMusicPlayer?.seek(Duration.ZERO)
        rainSoundsMusicPlayer?.play()
    }
    rainSoundsMusicPlayer?.play()
}

fun playCicadaSounds(dateTime: LocalDateTime) {
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
    ) return
    val file = getAsset("fireworks.mp3")
    val media = Media(file.toURI().toString())
    fireworkSoundsMusicPlayer = MediaPlayer(media)
    fireworkSoundsMusicPlayer?.setOnEndOfMedia {
        fireworkSoundsMusicPlayer?.seek(Duration.ZERO)
        fireworkSoundsMusicPlayer?.play()
    }
    fireworkSoundsMusicPlayer?.play()
}

fun stopPlayers() {
    currentEvent = null

    hourlyMusicPlayer?.stop()
    hourlyMusicPlayer = null

    rainSoundsMusicPlayer?.stop()
    rainSoundsMusicPlayer = null

    cicadaSoundsMusicPlayers.forEach { it.value.stop() }
    cicadaSoundsMusicPlayers = mutableMapOf()

    fireworkSoundsMusicPlayer?.stop()
    fireworkSoundsMusicPlayer = null
}

fun resetPlayers(dateTime: LocalDateTime, weather: Weather) {
    stopPlayers()

    timeLabel.text = twentyFourToTwelve(dateTime.hour).toNiceString()
    weatherLabel.icon = if (isNight(dateTime.hour)) weather.nightIcon else weather.dayIcon

    playHourlyMusic(dateTime, weather)
    playRainSounds(weather)
    playCicadaSounds(dateTime)
    playFireworkSounds()
}

fun resetPlayers() {
    val dateTime = LocalDateTime.now()
    val weather = getWeather()
    resetPlayers(dateTime, weather)
}
