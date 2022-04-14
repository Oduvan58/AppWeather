package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.room.HistoryDAO
import by.geekbrains.appweather.utils.convertHistoryEntityToWeather
import by.geekbrains.appweather.utils.convertWeatherToHistoryEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDAO) : LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToHistoryEntity(weather))
    }
}