package by.geekbrains.appweather.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.geekbrains.appweather.R
import by.geekbrains.appweather.domain.Weather

class HistoryFragmentAdapter : RecyclerView.Adapter<HistoryFragmentAdapter.HistoryViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_item, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount() = weatherData.size

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Weather) {
            itemView.findViewById<TextView>(R.id.title_city_card_view_text_view).text =
                data.city.name
        }
    }
}