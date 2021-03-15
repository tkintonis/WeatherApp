package com.example.weatherapp.main

import androidx.lifecycle.LiveData
import com.example.weatherapp.network.models.LocationResponse
import com.example.weatherapp.room.LocationDataBase
import com.example.weatherapp.room.LocationRepository
import com.example.weatherapp.room.entities.Location
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherUseCase(private val forecastApi: ForecastApi, private val locationDataBase: LocationDataBase) {

    private val repository: LocationRepository
    val readAllData: LiveData<List<Location>>

    init {
        val locationDao = locationDataBase.locationDao()
        repository = LocationRepository(locationDao)
        readAllData = repository.readAllLocations
    }

    fun getWeatherForecast(location: String): Single<LocationResponse> = forecastApi
        .getWeatherForecast(location,5,1,"json")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun addLocation(location: Location): Single<Any> =
        repository.addLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteLocation(location: Location) =
        repository.deleteLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun exists(location: String) =
        repository.exists(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}