package wtf.lpc.howl

import javax.swing.ImageIcon

enum class Weather(val icon: ImageIcon) {
    SUNNY(sunnyIcon),
    RAINY(rainyIcon),
    SNOWY(snowyIcon)
}

fun getWeather() = when (settings.weather) {
    WeatherSetting.AUTOMATIC -> Weather.SUNNY
    WeatherSetting.SUNNY -> Weather.SUNNY
    WeatherSetting.RAINY -> Weather.RAINY
    WeatherSetting.SNOWY -> Weather.SNOWY
}
