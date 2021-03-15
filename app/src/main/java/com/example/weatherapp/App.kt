package com.example.weatherapp

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.weatherapp.common.BASE_URL
import com.example.weatherapp.main.ForecastApi
import com.example.weatherapp.main.WeatherUseCase
import com.example.weatherapp.main.MainViewModel
import com.example.weatherapp.room.LocationDataBase
import com.example.weatherapp.room.RoomUseCase
import com.example.weatherapp.start.SplashViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("unused")
class App : Application() {

    private val startModule = module {
        viewModel { SplashViewModel() }
    }

    private val appModule = module {
        single {
            Room.databaseBuilder(
                this@App,
                LocationDataBase::class.java,
                "location_database"
            ).build()
        }
        single { RoomUseCase(get()) }
        single { get<Retrofit>().create(ForecastApi::class.java) }
        single { WeatherUseCase(get(),get()) }
        viewModel { MainViewModel(get()) }
    }

    private val networkModule = module {
        single {
            OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
                .build()
        }
        single { Gson() }
        single { GsonConverterFactory.create(get()) } bind Converter.Factory::class
        single {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(get())
                .client(get())
                .build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            koin.loadModules(listOf(networkModule, startModule, appModule))
            koin.createRootScope()
        }
    }
}

val Activity.app: App
    get() = this.application as App

val Fragment.app: App
    get() = this.requireActivity().app