package com.jesse.nasaapi.data.repository


import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import com.jesse.nasaapi.domain.Resource
import kotlinx.coroutines.flow.Flow

/**
 * The AstronomyPictureRepository interface is created for efficient testing when writing unit tests.
 * This makes mocking the AstronomyPictureRepository when writing tests possible and a lot more
 * convenient.
 * */
interface AstronomyPictureRepository {
    fun getAstronomyPictures(): Flow<Resource<List<AstronomyPictureFormattedUseCase>>>
}