package com.example.weatherapp.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.weatherapp.room.entities.Location
import retrofit2.http.DELETE

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLocation(location: Location)

    @Query("SELECT * FROM location_table ORDER BY location_name ASC")
    fun readAllLocations() : LiveData<List<Location>>

    @Delete
    fun deleteLocation(location: Location)

    @Query("SELECT EXISTS (SELECT 1 FROM location_table WHERE location_name = :location)")
    fun exists(location: String): Boolean
}