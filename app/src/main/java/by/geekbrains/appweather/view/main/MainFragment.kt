package by.geekbrains.appweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.geekbrains.appweather.R
import by.geekbrains.appweather.databinding.FragmentMainBinding
import by.geekbrains.appweather.domain.Weather
import by.geekbrains.appweather.view.details.DetailsFragment
import by.geekbrains.appweather.view.details.DetailsFragment.Companion.BUNDLE_WEATHER_KEY
import by.geekbrains.appweather.view.showSnackBar
import by.geekbrains.appweather.viewmodel.AppState
import by.geekbrains.appweather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            showDetailsWeather(weather)
        }
    })

    private var isDataSetRus: Boolean = true

    companion object {
        fun newInstance() = MainFragment()
    }

    private fun showDetailsWeather(weather: Weather) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.details_fragment_container,
                    DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(BUNDLE_WEATHER_KEY, weather)
                    }))
                .addToBackStack("")
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(binding.mainFragmentRecyclerView, adapter)
        binding.mainFragmentFAB.setOnClickListener {
            changeWeatherDataSet()
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() = if (isDataSetRus) {
        viewModel.getWeatherFromLocalSourceWorld()
        binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
    } else {
        viewModel.getWeatherFromLocalSourceRus()
        binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
    }.also { isDataSetRus = !isDataSetRus }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.mainFragmentFAB.showSnackBar(getString(R.string.error))
            }
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(weatherData)
            }
        }
    }

    private fun initRecycler(recyclerView: RecyclerView, adapter: MainFragmentAdapter) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}