package by.geekbrains.appweather.utils

import by.geekbrains.appweather.domain.FactDTO
import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.domain.WeatherDTO
import by.geekbrains.appweather.domain.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feelsLike!!,
        fact.condition!!, fact.icon))
}