package com.example.weatherapp

sealed class Resource<out T>

class Init<T> : Resource<T>()
class Loading<T> : Resource<T>()
class Success<T>(val data: T) : Resource<T>()
class Failure<T>(val message: String) : Resource<T>()

val <T> T.exhaustive
    get() = this
