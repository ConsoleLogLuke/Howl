package wtf.lpc.howl

import java.time.LocalDateTime
import java.util.*
import kotlin.concurrent.schedule

fun startMainLoop() {
    var previousHour: Int? = null
    var previousNewYearSong: String? = null
    Timer().schedule(0, 1) {
        val dateTime = LocalDateTime.now()
        val hour = dateTime.hour
        var newNewYearSong: String? = getNewYearSong(dateTime)
        if (
            currentEvent in listOf(Event.NEW_YEARS_DAY, Event.NEW_YEARS_EVE) &&
            previousNewYearSong != newNewYearSong
        ) previousNewYearSong = newNewYearSong
        else newNewYearSong = null
        if (previousHour != hour || newNewYearSong != null) {
            previousHour = hour
            val weather = getWeather()
            if (!paused) resetPlayers(dateTime, weather)
            else {
                val twelveHour = twentyFourToTwelve(hour)
                timeLabel.text = twelveHour.toNiceString()
                weatherLabel.icon = if (isNight(hour)) weather.nightIcon else weather.dayIcon
            }
        }
    }
}
