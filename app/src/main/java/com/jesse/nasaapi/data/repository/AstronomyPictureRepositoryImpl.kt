package com.jesse.nasaapi.data.repository

import com.jesse.nasaapi.data.database.AstronomyPictureDao
import com.jesse.nasaapi.data.network.AstronomyPictureService
import com.jesse.nasaapi.di.IoDispatcher
import com.jesse.nasaapi.domain.AstronomyPictureFormattedUseCase
import com.jesse.nasaapi.domain.AstronomyPictureWithUrlAndImageUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AstronomyPictureRepositoryImpl @Inject
constructor(private val astronomyPictureService: AstronomyPictureService,
@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
            private val astronomyPictureDao: AstronomyPictureDao,
            private val astronomyPictureUseCase: AstronomyPictureWithUrlAndImageUseCase
):
    AstronomyPictureRepository {

    private val API_KEY = "DEMO_KEY"

    override fun getAstronomyPictures(): Flow<List<AstronomyPictureFormattedUseCase>> =
         astronomyPictureUseCase(astronomyPictureDao.getAllAstronomyPictures())

    override suspend fun refreshAstronomyPictures() {
        coroutineScope {
            val astronomyPictures = withContext(ioDispatcher){
                astronomyPictureService.getAstronomyPictures(API_KEY, 5)
            }

            withContext(ioDispatcher){
                astronomyPictureDao.insertAll(astronomyPictures)
            }
        }
    }
}