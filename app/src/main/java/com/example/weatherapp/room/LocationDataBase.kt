package com.example.weatherapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.room.dao.LocationDao
import com.example.weatherapp.room.entities.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationDataBase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
}