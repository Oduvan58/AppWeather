package by.geekbrains.appweather.viewmodel

import androidx.lifecycle.MutableLiveData
import by.geekbrains.appweather.App.Companion.getHistoryDAO
import by.geekbrains.appweather.repository.LocalRepository
import by.geekbrains.appweather.repository.LocalRepositoryImpl

class HistoryViewModel(
    private val historyLiveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepositoryImpl: LocalRepository = LocalRepositoryImpl(getHistoryDAO())
) {

    fun getAllHistory() {
        historyLiveDataToObserve.postValue(AppState.Loading)
        historyLiveDataToObserve.postValue(AppState.Success(historyRepositoryImpl.getAllHistory()))
    }
}