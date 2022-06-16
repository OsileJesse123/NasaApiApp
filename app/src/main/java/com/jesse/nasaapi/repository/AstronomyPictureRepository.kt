package com.jesse.nasaapi.repository

import com.jesse.nasaapi.data.database.AstronomyPicture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AstronomyPictureRepository {

    suspend fun getAstronomyPictures(): Flow<List<AstronomyPicture>>
    suspend fun refreshAstronomyPictures()
}