package com.jesse.nasaapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "astronomy_picture_formatted")
data class AstronomyPictureFormattedUseCase(
    @PrimaryKey
    var hdUrl: String = "",
    var title: String = "")
