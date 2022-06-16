package com.jesse.nasaapi.di

import com.jesse.nasaapi.repository.AstronomyPictureRepository
import com.jesse.nasaapi.repository.AstronomyPictureRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAstronomyPictureRepository(repositoryImpl: AstronomyPictureRepositoryImpl):
            AstronomyPictureRepository
}