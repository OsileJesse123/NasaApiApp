package com.jesse.nasaapi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesse.nasaapi.data.model.AstronomyPicture
import kotlinx.coroutines.flow.Flow

@Dao
interface AstronomyPictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(astronomyPictures: List<AstronomyPicture>)

    @Query("select * from astronomy_table")
    fun getAllAstronomyPictures(): Flow<List<AstronomyPicture>>
}