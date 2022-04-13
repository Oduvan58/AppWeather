package by.geekbrains.appweather.view.details

import android.net.Uri
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
import com.bumptech.glide.Glide
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val weatherBundle: Weather by lazy {
        (arguments?.getParcelable(BUNDLE_WEATHER_KEY)) ?: Weather()
    }

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
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        getWeather()
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
                        getWeather()
                    })
            }
        }
    }

    private fun getWeather() {
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun setWeather(weather: Weather) {
        val city = weatherBundle.city
        with(binding) {
            cityNameTextView.text = city.name
            cityCoordinatesTextView.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            temperatureValueTextView.text = weather.temperature.toString()
            feelsLikeValueTextView.text = weather.feelsLike.toString()
            weatherConditionTextView.text = weather.condition

            weather.icon?.let {
                GlideToVectorYou.justLoadImage(
                    activity,
                    Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
                    weatherIconAppCompatImageView
                )
            }
        }
        Glide.with(this).load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
            .into(binding.cityIconAppCompatImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}