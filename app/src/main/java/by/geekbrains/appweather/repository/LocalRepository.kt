package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}