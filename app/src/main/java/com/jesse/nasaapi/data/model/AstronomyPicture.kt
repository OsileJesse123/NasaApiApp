package com.jesse.nasaapi.data.model

import com.squareup.moshi.Json

/**
 * This is the Model class of the object we are fetching from the server.
 * */
data class AstronomyPicture(
    val date: String,
    val explanation: String,
    @Json(name = "hdurl")
    val hdUrl: String,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "service_version")
    val serviceVersion: String,
    var title: String,
    val url: String
)
