package by.geekbrains.appweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.geekbrains.appweather.App.Companion.getHistoryDAO
import by.geekbrains.appweather.repository.LocalRepository
import by.geekbrains.appweather.repository.LocalRepositoryImpl

class HistoryViewModel(
    private val historyLiveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepositoryImpl: LocalRepository = LocalRepositoryImpl(getHistoryDAO()),
) : ViewModel() {

    fun getLiveData() = historyLiveDataToObserve

    fun getAllHistory() {
        historyLiveDataToObserve.value = AppState.Loading
        Thread {
            historyLiveDataToObserve.postValue(AppState.Success(historyRepositoryImpl.getAllHistory()))
        }.start()
    }
}