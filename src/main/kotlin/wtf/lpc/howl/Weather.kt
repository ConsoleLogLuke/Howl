package wtf.lpc.howl

import javax.swing.ImageIcon

enum class Weather(val dayIcon: ImageIcon, val nightIcon: ImageIcon) {
    SUNNY(sunnyIcon, clearNightIcon),
    RAINY(rainyIcon, rainyIcon),
    SNOWY(snowyIcon, snowyIcon)
}

fun isNight(hour: Int) = hour <= 6 || hour >= 19

fun getWeather() = when (settings.weather) {
    WeatherSetting.AUTOMATIC -> Weather.SUNNY // TODO Give this a proper implementation
    WeatherSetting.SUNNY -> Weather.SUNNY
    WeatherSetting.RAINY -> Weather.RAINY
    WeatherSetting.SNOWY -> Weather.SNOWY
}
