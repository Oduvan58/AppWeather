package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
}