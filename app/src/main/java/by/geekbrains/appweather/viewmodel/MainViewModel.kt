package by.geekbrains.appweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.appweather.repository.Repository
import by.geekbrains.appweather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(),
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getWeatherFromRemoteSource() = getDataFromRemoteSource()

    private fun getDataFromRemoteSource() {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromServer()))
        }.start()
    }
}