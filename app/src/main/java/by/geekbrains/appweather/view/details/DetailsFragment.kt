package by.geekbrains.appweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.geekbrains.appweather.R
import by.geekbrains.appweather.databinding.FragmentDetailsBinding
import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.utils.showSnackBar
import by.geekbrains.appweather.viewmodel.AppState
import by.geekbrains.appweather.viewmodel.DetailsViewModel

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

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
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
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat,
            weatherBundle.city.lon)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainContainer.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.mainContainer.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainContainer.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainContainer.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat,
                            weatherBundle.city.lon)
                    })
            }
        }
    }

    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        binding.cityNameTextView.text = city.name
        binding.cityCoordinatesTextView.text = String.format(
            getString(R.string.city_coordinates),
            city.lat.toString(),
            city.lon.toString()
        )
        binding.temperatureValueTextView.text = weather.temperature.toString()
        binding.feelsLikeValueTextView.text = weather.feelsLike.toString()
        binding.weatherConditionTextView.text = weather.condition
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}