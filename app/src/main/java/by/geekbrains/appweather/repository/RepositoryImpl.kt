package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.Weather

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }
}