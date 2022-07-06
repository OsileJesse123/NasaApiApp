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

    /**This function inserts a list of AstronomyPictureFormattedUseCase into the database.
    * The conflict strategy here replaces old data with the same primary key with a new one.
    *  */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(astronomyPictures: List<AstronomyPictureFormattedUseCase>)

    /***
     * This fetches a flow of a list of AstronomyPictureFormattedUseCase from the database
     */
    @Query("select * from astronomy_picture_formatted")
    fun getAllAstronomyPictures(): Flow<List<AstronomyPictureFormattedUseCase>>
}