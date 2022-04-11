package by.geekbrains.appweather.repository

import by.geekbrains.appweather.domain.WeatherDTO
import by.geekbrains.appweather.utils.YANDEX_API_KEY_NAME
import by.geekbrains.appweather.utils.YANDEX_API_URL_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET(YANDEX_API_URL_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): Call<WeatherDTO>
}