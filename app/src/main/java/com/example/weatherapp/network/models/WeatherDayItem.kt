package com.example.weatherapp.network.models

data class WeatherDayItem(
    val date: String,
    val maxtempC: String,
    val maxtempF: String,
    val mintempC: String,
    val mintempF: String,
    val avgtempC: String,
    val avgtempF: String,
    val totalSnow_cm: String,
    val sunHour: String,
    val hourly: List<HourForecast>,
    var expanded : Boolean = true,
    var position : Int
) {

    fun formatDate(): String {
        return "Weather on : $date"
    }

    fun formatMaxTemp(): String {
        return "Maximum Temperature : $maxtempC °C"
    }
    fun formatMinTemp(): String {
        return "Minimum Temperature : $mintempC °C"
    }
    fun formatAvgTemp(): String {
        return "Average Temperature : $avgtempC °C"
    }
}