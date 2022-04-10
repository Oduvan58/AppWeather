package by.geekbrains.appweather.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: FactDTO?,
) : Parcelable