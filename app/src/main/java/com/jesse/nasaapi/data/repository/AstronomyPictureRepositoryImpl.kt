package com.jesse.nasaapi.data.repository

import android.util.Log
import com.jesse.nasaapi.data.database.AstronomyPictureDao
import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import com.jesse.nasaapi.data.network.AstronomyPictureService
import com.jesse.nasaapi.di.DefaultDispatcher
import com.jesse.nasaapi.di.IoDispatcher
import com.jesse.nasaapi.domain.AstronomyPictureWithUrlAndImageUseCase
import com.jesse.nasaapi.domain.Resource
import com.jesse.nasaapi.domain.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AstronomyPictureRepositoryImpl @Inject
constructor(private val astronomyPictureService: AstronomyPictureService,
@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
            private val astronomyPictureDao: AstronomyPictureDao,
            private val astronomyPictureUseCase: AstronomyPictureWithUrlAndImageUseCase,
            @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
):
    AstronomyPictureRepository {

    private val API_KEY = "DEMO_KEY"

    override fun getAstronomyPictures(): Flow<Resource<List<AstronomyPictureFormattedUseCase>>> = networkBoundResource(
        query = {astronomyPictureDao.getAllAstronomyPictures()},
        fetch = suspend {
            coroutineScope {
                withContext(ioDispatcher){
                    Log.e("Querying", "Querying")
                    astronomyPictureService.getAstronomyPictures(API_KEY, 5)
                }
            }
        },
        saveFetchData = {
            pictures ->
            coroutineScope {
                withContext(ioDispatcher){
                    astronomyPictureDao.insertAll(
                        withContext(defaultDispatcher){
                            astronomyPictureUseCase(pictures)
                        }
                    )
                }
            }
        }
    )
}