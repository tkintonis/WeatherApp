package com.example.weatherapp.start

import androidx.lifecycle.ViewModel
import com.example.weatherapp.ViewModelDisposer
import com.example.weatherapp.VoidLiveEvent

class SplashViewModel : ViewModel() {

    private val disposer = ViewModelDisposer()
    val toMainScreenEvent = VoidLiveEvent()

    fun toMainScreen() {
        toMainScreenEvent.call()
    }

    override fun onCleared() {
        super.onCleared()
        disposer.disposeAll()
    }
}