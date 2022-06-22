package com.jesse.nasaapi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesse.nasaapi.data.model.AstronomyPicture
import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import com.jesse.nasaapi.domain.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyPictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(astronomyPictures: List<AstronomyPictureFormattedUseCase>)

    @Query("select * from astronomy_picture_formatted")
    fun getAllAstronomyPictures(): Flow<List<AstronomyPictureFormattedUseCase>>
}