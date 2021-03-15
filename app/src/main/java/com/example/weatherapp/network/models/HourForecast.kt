package com.example.weatherapp.network.models

data class HourForecast(
    val time: String,
    val tempC: String,
    val tempF: String,
    val windspeedMiles: String,
    val windspeedKmph: String,
    val winddirDegree: String,
    val winddir16Point: String,
    val weatherCode: String,
    val weatherIconUrl: List<ValueItem>,
    val weatherDesc: List<ValueItem>,
    val precipMM: String,
    val precipInches: String,
    val humidity: String,
    val visibility: String,
    val visibilityMiles: String,
    val pressure: String,
    val pressureInches: String,
    val cloudcover: String,
    val HeatIndexC: String,
    val HeatIndexF: String,
    val DewPointC: String,
    val DewPointF: String,
    val WindChillC: String,
    val WindChillF: String,
    val WindGustMiles: String,
    val WindGustKmph: String,
    val FeelsLikeC: String,
    val FeelsLikeF: String,
    val chanceofrain: String,
    val chanceofremdry: String,
    val chanceofwindy: String,
    val chanceofovercast: String,
    val chanceofsunshine: String,
    val chanceoffrost: String,
    val chanceofhightemp: String,
    val chanceoffog: String,
    val chanceofsnow: String,
    val chanceofthunder: String
) {

    fun getTemp(): String {
        return "$tempC °C"
    }

    fun getHour(): String {
        return when (time) {
            "0" -> "0:00"
            "100" -> "01:00"
            "200" -> "02:00"
            "300" -> "03:00"
            "400" -> "04:00"
            "500" -> "05:00"
            "600" -> "06:00"
            "700" -> "07:00"
            "800" -> "08:00"
            "900" -> "09:00"
            "1000" -> "10:00"
            "1100" -> "11:00"
            "1200" -> "12:00"
            "1300" -> "13:00"
            "1400" -> "14:00"
            "1500" -> "15:00"
            "1600" -> "16:00"
            "1700" -> "17:00"
            "1800" -> "18:00"
            "1900" -> "19:00"
            "2000" -> "20:00"
            "2100" -> "21:00"
            "2200" -> "22:00"
            "2300" -> "23:00"
            else -> ""
        }
    }
}