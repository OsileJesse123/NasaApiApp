package com.jesse.nasaapi.di

import android.content.Context
import androidx.room.Room
import com.jesse.nasaapi.data.database.AstronomyPictureDao
import com.jesse.nasaapi.data.database.AstronomyPictureDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  This acts as a container for the necessary dependencies related to the database
 * */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAstronomyDatabase(@ApplicationContext context: Context): AstronomyPictureDatabase{
        return Room.databaseBuilder(context, AstronomyPictureDatabase::class.java,
            "astronomy_picture.db")
            .build()
    }
    @Provides
    @Singleton
    fun provideAstronomyPictureDao(database: AstronomyPictureDatabase): AstronomyPictureDao{
        return database.getAstronomyPictureDao()
    }
}