package by.geekbrains.appweather.utils

import by.geekbrains.appweather.domain.*
import by.geekbrains.appweather.room.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feelsLike!!,
        fact.condition!!, fact.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>) = entityList.map {
    Weather(City(it.name, 0.0, 0.0), it.temperature, 0, it.condition)
}

fun convertWeatherToHistoryEntity(weather: Weather) =
    HistoryEntity(0, weather.city.name, weather.temperature, weather.condition)
