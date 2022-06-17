package com.jesse.nasaapi.data.repository

import com.jesse.nasaapi.data.database.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow

interface AstronomyPictureRepository {

    suspend fun getAstronomyPictures(): Flow<List<AstronomyPicture>>
    suspend fun refreshAstronomyPictures()
}