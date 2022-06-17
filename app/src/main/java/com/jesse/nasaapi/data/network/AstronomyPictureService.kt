package com.jesse.nasaapi.data.network


import com.jesse.nasaapi.data.database.model.AstronomyPicture
import retrofit2.http.GET
import retrofit2.http.Query

interface AstronomyPictureService {

    @GET("apod")
    suspend fun getAstronomyPictures(@Query("api_key") apiKey: String,
                                     @Query("count") count: Int): List<AstronomyPicture>
}