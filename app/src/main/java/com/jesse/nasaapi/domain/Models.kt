package com.jesse.nasaapi.domain

import com.jesse.nasaapi.data.model.AstronomyPicture
import com.jesse.nasaapi.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AstronomyPictureWithUrlAndImageUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher){

    operator fun invoke(pictureFlow: Flow<List<AstronomyPicture>>): Flow<List<AstronomyPictureFormattedUseCase>>{
        return pictureFlow.map { list ->
             val newList = list.map {
                AstronomyPictureFormattedUseCase(it.url, formatText(it.title))
            }
            return@map newList
        }.flowOn(defaultDispatcher)
    }

    private fun formatText(text: String): String =
        if (text.length > 32) {
            text.dropLast(text.length - 28) + "..."
        } else {
            text
        }
}

data class AstronomyPictureFormattedUseCase(val hdUrl: String, val title: String)