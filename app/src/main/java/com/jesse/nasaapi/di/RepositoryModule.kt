package com.jesse.nasaapi.di

import com.jesse.nasaapi.data.repository.AstronomyPictureRepository
import com.jesse.nasaapi.data.repository.AstronomyPictureRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  This acts as a container for the necessary dependencies related to the Repository
 * */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAstronomyPictureRepository(repositoryImpl: AstronomyPictureRepositoryImpl):
            AstronomyPictureRepository
}