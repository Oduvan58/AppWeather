package by.geekbrains.appweather.viewmodel

import by.geekbrains.appweather.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
