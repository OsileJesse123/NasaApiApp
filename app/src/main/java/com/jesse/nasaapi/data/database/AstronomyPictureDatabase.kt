package com.jesse.nasaapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesse.nasaapi.data.model.AstronomyPicture
import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase

@Database(entities = [AstronomyPictureFormattedUseCase::class], version = 1)
abstract class AstronomyPictureDatabase: RoomDatabase() {

    abstract fun getAstronomyPictureDao(): AstronomyPictureDao
}