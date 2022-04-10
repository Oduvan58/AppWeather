package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.WeatherDTO
import retrofit2.Callback

interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>,
    )
}