package by.geekbrains.appweather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.geekbrains.appweather.R
import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.view.main.MainFragmentAdapter.MainViewHolder

class MainFragmentAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MainViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_main_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount() = weatherData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameCity = itemView.findViewById<TextView>(R.id.title_city_card_view_text_view)

        fun bind(weather: Weather) = with(itemView) {
            nameCity.text = weather.city.name
            setOnClickListener {
                onItemViewClickListener?.onItemViewClick(weather)
            }
        }
    }
}