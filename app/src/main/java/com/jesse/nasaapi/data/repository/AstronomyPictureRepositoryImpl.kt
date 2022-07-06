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

/**
 * This is an implementation of the AstronomyPictureRepository interface.
 * It is used to communicate with the source of data and send t down the data
 * to the UI layer.
 * */
class AstronomyPictureRepositoryImpl @Inject
constructor(
    // This property is used to make API calls. It is injected using hilt.
    private val astronomyPictureService: AstronomyPictureService,

    // This property is used to dispatch operations such as database or network calls
    // on the IO thread. The property is injected using hilt.
@IoDispatcher private val ioDispatcher: CoroutineDispatcher,

            // This property is used to access the database like reading from and writing into it.
            // It is injected using hilt.
            private val astronomyPictureDao: AstronomyPictureDao,

            // This property is used to convert AstronomyPicture objects to
            // AstronomyPictureWithUrlAndImageUseCase objects. It is injected using hilt.
            private val astronomyPictureUseCase: AstronomyPictureWithUrlAndImageUseCase,

            // This property is used to dispatch operations such as sorting a list
            // on the Default thread. The property is injected using hilt.
            @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
):
    AstronomyPictureRepository {

    /**
     * This property is used as a query when making API calls. It is essential when making these
     * calls. The value "DEMO_KEY" can be used to make API calls but is heavily limited. You can
     * also retrieve your personal API key from the NASA API 'https://api.nasa.gov'.
     * */
    private val API_KEY = "DEMO_KEY"


    /**
     * This function is used to fetch data from the NASA server and caches the data into the Room
     * Database. It then sends this data downstream to the UI layer directly from the Room Database.
     * The data is first wrapped in the a Resource object before being sent down to the UI layer.
     * This is all done using the NetworkBoundResource strategy.
     * */
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