package com.example.weatherapp.main

import com.example.weatherapp.common.KEY_PASS
import com.example.weatherapp.network.models.LocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ForecastApi {

    @GET("/premium/v1/weather.ashx?key=$KEY_PASS")
    fun getWeatherForecast(
        @Query("q") location: String,
        @Query("num_of_days") numberOfDays: Int = 5,
        @Query("tp") tp: Int = 1,
        @Query("format") format: String = "json",
        @Query("showmap") withIcons: String = "yes",
    ): Single<LocationResponse>
}