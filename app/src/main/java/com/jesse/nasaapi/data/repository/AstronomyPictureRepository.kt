package com.jesse.nasaapi.data.repository


import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import com.jesse.nasaapi.domain.Resource
import kotlinx.coroutines.flow.Flow

interface AstronomyPictureRepository {
    fun getAstronomyPictures(): Flow<Resource<List<AstronomyPictureFormattedUseCase>>>
}