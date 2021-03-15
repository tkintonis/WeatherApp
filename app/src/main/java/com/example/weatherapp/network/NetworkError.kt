package com.example.weatherapp.network

import com.example.weatherapp.Failure
import com.example.weatherapp.network.models.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException

private val gson = Gson()

fun <T> Throwable.mapFailure(): Failure<T> =
    if (this is HttpException) {
        this.response()?.errorBody()?.string()
            ?.let { gson.fromJson(it, ErrorResponse::class.java) }
            ?.let { Failure<T>(it.data.errorList[0].msg) }
            ?: Failure<T>(this.message())
    } else {
        Failure<T>(this.message ?: "")
    }