package com.example.weatherapp.network.models

data class CurrentConditionItem(
    val observation_time: String,
    val temp_C: String,
    val temp_F: String,
    val weatherIconUrl: List<ValueItem>,
    val weatherDesc: List<ValueItem>,
    val windspeedMiles: String,
    val windspeedKmph: String,
    val winddirDegree: String,
    val winddir16Point: String,
    val precipMM: String,
    val precipInches: String,
    val humidity: String,
    val visibility: String,
    val visibilityMiles: String,
    val pressure: String,
    val pressureInches: String,
    val cloudcover: String,
    val FeelsLikeC: String,
    val FeelsLikeF: String
)