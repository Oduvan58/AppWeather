package by.geekbrains.appweather.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: FactDTO?,
) : Parcelable