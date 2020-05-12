package wtf.lpc.howl

import javax.swing.ImageIcon

enum class Weather(val dayIcon: ImageIcon, val nightIcon: ImageIcon) {
    SUNNY(sunnyIcon, clearNightIcon),
    RAINY(rainyIcon, rainyIcon),
    SNOWY(snowyIcon, snowyIcon)
}

fun getWeather() = when (settings.weather) {
    WeatherSetting.AUTOMATIC -> Weather.SUNNY
    WeatherSetting.SUNNY -> Weather.SUNNY
    WeatherSetting.RAINY -> Weather.RAINY
    WeatherSetting.SNOWY -> Weather.SNOWY
}
