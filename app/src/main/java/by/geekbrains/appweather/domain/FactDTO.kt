package by.geekbrains.appweather.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FactDTO(
    val temp: Int?,
    @SerializedName("feels_like")
    val feelsLike: Int?,
    val condition: String?,
    val icon: String?,
) : Parcelable
