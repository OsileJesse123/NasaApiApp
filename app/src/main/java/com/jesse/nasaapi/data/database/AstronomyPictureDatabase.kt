package com.jesse.nasaapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AstronomyPicture::class], version = 1)
abstract class AstronomyPictureDatabase: RoomDatabase() {

    abstract fun getAstronomyPictureDao(): AstronomyPictureDao
}