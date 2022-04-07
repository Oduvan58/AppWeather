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
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.postValue(AppState.Loading)
        Thread {
            sleep(2000)
            if (isRussian) {
                liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorageRus()))
            } else {
                liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorageWorld()))
            }
        }.start()
    }
}