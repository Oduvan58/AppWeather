package by.geekbrains.appweather.view.details

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.geekbrains.appweather.BuildConfig
import by.geekbrains.appweather.R
import by.geekbrains.appweather.databinding.FragmentDetailsBinding
import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.repository.WeatherDTO
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

private const val PROCESS_ERROR = "Обработка ошибки"
private const val MAIN_LINK = "https://api.weather.yandex.ru/v2/informers?"
private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    companion object {
        const val BUNDLE_WEATHER_KEY = "BUNDLE_WEATHER_KEY"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_WEATHER_KEY) ?: Weather()
        getWeather()
    }

    private fun getWeather() {
        binding.mainContainer.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val client = OkHttpClient()
        val builder: Request.Builder = Request.Builder()
        builder.header(REQUEST_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url(MAIN_LINK + "lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            val handler: Handler = Handler()

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val serverResponse: String? = response.body()?.string()
                if (response.isSuccessful && serverResponse != null) {
                    handler.post {
                        showWeather(Gson().fromJson(serverResponse,
                            WeatherDTO::class.java))
                    }
                } else {
                    TODO(PROCESS_ERROR)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                TODO(PROCESS_ERROR)
            }
        })
    }

    private fun showWeather(weatherDTO: WeatherDTO) {
        binding.mainContainer.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        val fact = weatherDTO.fact
        if (fact?.temp == null || fact.feelsLike == null ||
            fact.condition.isNullOrEmpty()
        ) {
            TODO(PROCESS_ERROR)
        } else {
            val city = weatherBundle.city
            binding.cityNameTextView.text = city.name
            binding.cityCoordinatesTextView.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            binding.temperatureValueTextView.text = fact.temp.toString()
            binding.feelsLikeValueTextView.text = fact.feelsLike.toString()
            binding.weatherConditionTextView.text = fact.condition
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}