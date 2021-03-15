package com.example.weatherapp.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.*
import com.example.weatherapp.network.mapFailure
import com.example.weatherapp.network.models.CurrentConditionItem
import com.example.weatherapp.network.models.LocationResponse
import com.example.weatherapp.network.models.WeatherDayItem
import com.example.weatherapp.room.entities.Location

class MainViewModel(private val useCase: WeatherUseCase) : ViewModel() {

    private val disposer = ViewModelDisposer()
    val searchQuery = MutableLiveData<String>("")
    val emptyDataText = MutableLiveData<String>("")
    val isLoading = MutableLiveData<Boolean>(false)
    val hasForecastData = MutableLiveData<Boolean>(false)
    val forecastResult = MutableLiveData<Resource<LocationResponse>>(Init())
    val daysForecastList = MutableLiveData<List<WeatherDayItem>>(emptyList())
    val currentWeatherForecast = MutableLiveData<CurrentConditionItem>()
    val currentLocation = MutableLiveData<String>("")
    val locationRoomDataList: LiveData<List<Location>> = useCase.readAllData
    val invalidateAdapterEvent = VoidLiveEvent()
    val existsInList = MutableLiveData<Boolean>(false)
    val actionMessageEvent = SingleLiveEvent<String>()
    val selectedWeatherItem = MutableLiveData<WeatherDayItem>()

    @SuppressLint("CheckResult")
    fun getWeatherForecast() {
        val location = searchQuery.value ?: ""
        useCase.getWeatherForecast(location)
            .doOnSubscribe(disposer::addDisposable)
            .doOnSubscribe { forecastResult.value = Loading() }
            .doAfterTerminate { forecastResult.value = Init() }
            .subscribe(
                {
                    forecastResult.value = Success(it)
                    handleResponse(it)
                },
                {
                    forecastResult.value = it.mapFailure()
                })
    }

    private fun handleResponse(response: LocationResponse) {
        if (!response.data.weather.isNullOrEmpty()) {
            hasForecastData.value = true
            daysForecastList.value = response.data.weather ?: emptyList()
            currentWeatherForecast.value = response.data.current_condition[0]
            currentLocation.value = response.data.request[0].query
            searchQuery.value = currentLocation.value
            existsInList()
        } else {
            hasForecastData.value = false
            emptyDataText.value = response.data.error[0].msg
        }
    }

    @SuppressLint("CheckResult")
    fun addLocation() {
        val location: String = currentLocation.value ?: ""
        val newLocation = Location(location)
        useCase.addLocation(newLocation)
            .doOnSubscribe(disposer::addDisposable)
            .subscribe({
                existsInList()
                actionMessageEvent.value = "${currentLocation.value} added to your list"
            }, {})
    }

    @SuppressLint("CheckResult")
    fun deleteLocation() {
        val location: String = currentLocation.value ?: ""
        val tobeRemoved = Location(location)
        useCase.deleteLocation(tobeRemoved)
            .doOnSubscribe(disposer::addDisposable)
            .subscribe({
                invalidateAdapterEvent.call()
                existsInList()
                actionMessageEvent.value = "${currentLocation.value} removed from your list"
            }, {

            })
    }

    @SuppressLint("CheckResult")
    fun existsInList() {
        useCase.exists(currentLocation.value!!)
            .doOnSubscribe(disposer::addDisposable)
            .subscribe({
                existsInList.value = it
            }, {

            })
    }

    fun clearSearchField() {
        searchQuery.value = ""
        hasForecastData.value = false
        currentLocation.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        disposer.disposeAll()
    }
}