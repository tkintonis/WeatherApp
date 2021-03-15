package com.example.weatherapp.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class Location(
    @PrimaryKey
    val location_name: String
)