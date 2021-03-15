package com.example.weatherapp.network.models

data class WeatherResult(
    val request: List<LocationItem>,
    val current_condition: List<CurrentConditionItem>,
    val weather: List<WeatherDayItem>?,
    val error: List<ErrorItem>
)