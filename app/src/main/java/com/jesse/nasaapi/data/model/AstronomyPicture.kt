package com.jesse.nasaapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "astronomy_table")
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
    @PrimaryKey
    val url: String
)
