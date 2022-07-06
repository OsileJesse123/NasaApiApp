package com.jesse.nasaapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The UI really only needs the AstronomyPicture object's image Url (in this case hdUrl) and title
 * which will be displayed on a card. So the AstronomyPictureFormattedUseCase is a formatted
 * Model of the AstronomyPicture Object that consists of the properties that the UI really needs.
 * */
@Entity(tableName = "astronomy_picture_formatted")
data class AstronomyPictureFormattedUseCase(
    @PrimaryKey
    var hdUrl: String = "",
    var title: String = "")
