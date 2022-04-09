package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.domain.getRussianCities
import by.geekbrains.appweather.domain.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromRemoteSource() = Weather()

    override fun getWeatherFromLocalSource() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}