package com.example.weatherapp.room

import androidx.lifecycle.LiveData
import com.example.weatherapp.room.dao.LocationDao
import com.example.weatherapp.room.entities.Location
import io.reactivex.Single
import java.lang.Exception
import java.lang.IllegalStateException

class LocationRepository(private val locationDao: LocationDao) {

    val readAllLocations: LiveData<List<Location>> = locationDao.readAllLocations()

    fun addLocation(location: Location): Single<Any> {
        return Single.create<Any> {
            try {
                locationDao.addLocation(location)
                it.onSuccess(true)
            } catch (exception : Exception) {
                it.onError(IllegalStateException("Couldn't save data"))
            }
        }
    }

    fun deleteLocation(location: Location) : Single<Any> {
        return Single.create<Any> {
            try {
                locationDao.deleteLocation(location)
                it.onSuccess(true)
            } catch (exception : Exception) {
                it.onError(IllegalStateException("Couldn't delete data"))
            }
        }
    }

    fun exists(location: String) : Single<Boolean> {
        return Single.create<Boolean> {
            try {
                it.onSuccess(locationDao.exists(location))
            } catch (exception : Exception) {
                it.onError(IllegalStateException("Couldn't delete data"))
            }
        }
    }
}