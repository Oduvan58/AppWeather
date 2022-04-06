package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.domain.getRussianCities
import by.geekbrains.appweather.domain.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromRemoteSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }
}