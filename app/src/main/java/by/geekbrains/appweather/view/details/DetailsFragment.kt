package by.geekbrains.appweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.geekbrains.appweather.R
import by.geekbrains.appweather.databinding.FragmentDetailsBinding
import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.repository.WeatherDTO
import by.geekbrains.appweather.repository.WeatherLoader
import by.geekbrains.appweather.view.AlertDialogFragment

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

    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                showWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                throwable.message?.let {
                    AlertDialogFragment().show(childFragmentManager,
                        AlertDialogFragment.DIALOG_FRAGMENT_TAG)
                }
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
        binding.mainContainer.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val loader = WeatherLoader(onLoadListener, weatherBundle.city.lat, weatherBundle.city.lon)
        loader.loadWeather()
    }

    private fun showWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            mainContainer.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            val city = weatherBundle.city
            cityNameTextView.text = city.name
            cityCoordinatesTextView.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            weatherConditionTextView.text = weatherDTO.fact?.condition
            temperatureValueTextView.text = weatherDTO.fact?.temp.toString()
            feelsLikeValueTextView.text = weatherDTO.fact?.feelsLike.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}