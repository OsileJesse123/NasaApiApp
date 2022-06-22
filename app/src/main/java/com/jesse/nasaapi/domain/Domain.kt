package com.jesse.nasaapi.domain

import com.jesse.nasaapi.data.model.AstronomyPicture
import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class AstronomyPictureWithUrlAndImageUseCase @Inject constructor(
    ){

    operator fun invoke(pictures: List<AstronomyPicture>): List<AstronomyPictureFormattedUseCase>{
         return pictures.map {
             AstronomyPictureFormattedUseCase(it.url, formatText(it.title))
         }
    }

    private fun formatText(text: String): String =
        if (text.length > 32) {
            text.dropLast(text.length - 28) + "..."
        } else {
            text
        }
}


sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
){
    class Loading<T>(data: T?): Resource<T>(data = data)
    class Success<T>(data: T): Resource<T>(data = data)
    class Failed<T>(error: Throwable, data: T?): Resource<T>(data, error)
}

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchData: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = {true}
) =
    flow {
        val data = query().first()

        val flow = if(shouldFetch(data)){

            emit(Resource.Loading(data))

            try {
                saveFetchData(fetch())
                query().map {
                    result ->

                    Resource.Success(result)
                }
            } catch (e: Exception){
                query().map {
                    result ->
                    Resource.Failed(e, result)
                }
            }
        } else {
            query().map {
                result ->
                Resource.Success(result)
            }
        }

        emitAll(flow)
    }