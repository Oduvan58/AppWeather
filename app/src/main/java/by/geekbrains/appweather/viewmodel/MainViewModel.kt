package by.geekbrains.appweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.appweather.repository.Repository
import by.geekbrains.appweather.repository.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        with(liveDataToObserve) {
            postValue(AppState.Loading)
            if (isRussian) {
                postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorageRus()))
            } else {
                postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorageWorld()))
            }
        }
    }
}