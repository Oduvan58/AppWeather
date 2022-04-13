package by.geekbrains.appweather.repository

import by.geekbrains.appweather.BuildConfig
import by.geekbrains.appweather.domain.WeatherDTO
import by.geekbrains.appweather.utils.YANDEX_API_URL
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val weatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(YANDEX_API_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build().create(WeatherApi::class.java)
    }

    fun getWeatherDetails(
        lat: Double, lon: Double,
        callback: Callback<WeatherDTO>,
    ) {
        weatherApi.getWeather(BuildConfig.WEATHER_API_KEY, lat,
            lon).enqueue(callback)
    }
}