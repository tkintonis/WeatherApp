package com.example.weatherapp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ViewModelDisposer {

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun disposeAll() {
        compositeDisposable.clear()
    }
}