package com.jesse.nasaapi.data.network


import com.jesse.nasaapi.data.model.AstronomyPicture
import retrofit2.http.GET
import retrofit2.http.Query

interface AstronomyPictureService {

    /**
     * This function is responsible for fetching the list of AstronomyPicture as an array of JSON
     * objects from the server. The api_key is a necessary query required to make API requests.
     * The count query determines how many AstronomyPicture objects will be returned. The max
     * number allowed for count is 30.
     * */
    @GET("apod")
    suspend fun getAstronomyPictures(@Query("api_key") apiKey: String,
                                     @Query("count") count: Int): List<AstronomyPicture>
}