package com.jesse.nasaapi.data.repository

import com.jesse.nasaapi.domain.AstronomyPictureFormattedUseCase
import kotlinx.coroutines.flow.Flow

interface AstronomyPictureRepository {

    fun getAstronomyPictures(): Flow<List<AstronomyPictureFormattedUseCase>>
    suspend fun refreshAstronomyPictures()
}