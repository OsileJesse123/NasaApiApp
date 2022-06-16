package com.jesse.nasaapi.repository

import android.util.Log
import com.jesse.nasaapi.data.database.AstronomyPicture
import com.jesse.nasaapi.data.database.AstronomyPictureDao
import com.jesse.nasaapi.data.network.AstronomyPictureService
import com.jesse.nasaapi.di.DefaultDispatcher
import com.jesse.nasaapi.di.IoDispatcher
import com.jesse.nasaapi.formatText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AstronomyPictureRepositoryImpl @Inject
constructor(private val astronomyPictureService: AstronomyPictureService,
@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
            private val astronomyPictureDao: AstronomyPictureDao,
            @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
):
    AstronomyPictureRepository {

    private val API_KEY = "DEMO_KEY"



    override suspend fun getAstronomyPictures(): Flow<List<AstronomyPicture>> {
        return coroutineScope {
//            val astronomyPictures = withContext(ioDispatcher){
//                astronomyPictureService.getAstronomyPictures(API_KEY, 30)
//            }
//            Log.e("ThePictures", "Network Pictures: $astronomyPictures")
//
//            withContext(ioDispatcher){
//                astronomyPictureDao.insertAll(astronomyPictures)
//            }

                astronomyPictureDao.getAllAstronomyPictures().map { list ->
                    list.forEach {
                        it.title = formatText(it.title)
                    }
                    return@map list
                }.flowOn(defaultDispatcher)
        }
    }

    override suspend fun refreshAstronomyPictures() {
        coroutineScope {
            val astronomyPictures = withContext(ioDispatcher){
                astronomyPictureService.getAstronomyPictures(API_KEY, 30)
            }
            Log.e("ThePictures", "Network Pictures: $astronomyPictures")

            withContext(ioDispatcher){
                astronomyPictureDao.insertAll(astronomyPictures)
            }
        }
    }
}