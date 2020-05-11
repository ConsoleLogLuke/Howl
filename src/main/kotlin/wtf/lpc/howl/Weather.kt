package wtf.lpc.howl

enum class Weather { SUNNY, RAINY, SNOWY }

fun getWeather() = when (settings.weather) {
    WeatherSetting.AUTOMATIC -> Weather.SUNNY
    WeatherSetting.SUNNY -> Weather.SUNNY
    WeatherSetting.RAINY -> Weather.RAINY
    WeatherSetting.SNOWY -> Weather.SNOWY
}
