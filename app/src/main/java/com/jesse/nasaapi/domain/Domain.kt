package com.jesse.nasaapi.domain

import com.jesse.nasaapi.data.model.AstronomyPicture
import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * AstronomyPictureWithUrlAndImageUseCase is used to convert the AstronomyPicture object gotten from
 * the API to AstronomyPictureFormattedUseCase which holds the properties needed by the UI to display
 * to the users.
 * */
class AstronomyPictureWithUrlAndImageUseCase @Inject constructor(
    ){

    /**
     * This function is responsible for converting the AstronomyPicture object to
     * AstronomyPictureFormattedUseCase
     * */
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

/**
 * @Resource: acts as a wrapper around the results we get when fetching data from the data source.
 * It is also used to represent state of the UI at that point in time.
 *
 * @Note: Resource class is made generic to make it Reusable. T here acts the data type.
 *
 * @Loading state: means that the data is still being fetched from the data source. It takes data as
 * a parameter. Data is a nullable because while fresh data is being fetched from the data source
 * there might or might not be data already cached in local storage. If data is cached in local
 * storage, it is shown to the user first while fresh data is being fetched from the data source.
 * Else, a progress bar is displayed.
 *
 * @Success state: means that the data was successfully fetched from the data source. Here data is
 * not a nullable as it expected that the data required was "successfully" fetched from the
 * data source.
 *
 * @Failed state: means that the fetch data operation failed. This takes 2 parameters,
 * error (a throwable) and data (a nullable Object type). The error parameter would be sent down
 * to the UI layer, this would then use it to display the appropriate error message. The data
 * parameter here is also a nullable as data might or might not be cached in local storage to be
 * displayed to the user even after failure.
 * */
sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
){
    class Loading<T>(data: T?): Resource<T>(data = data)
    class Success<T>(data: T): Resource<T>(data = data)
    class Failed<T>(error: Throwable, data: T?): Resource<T>(data, error)
}

/**
 * @networkBoundResource function is the strategy we use to fetch data from the API, cache into
 * local storage and then display the data fetched making local storage the Source of Truth.
 * It returns a Flow of the ResultType.
 *
 * @ResultType: is the object type which the UI layer would be needing. This type would be cached
 * into local storage and sent over to the UI layer as per request. In our case, a list of
 * AstronomyPictureFormattedUseCase acts as the ResultType.
 *
 * @RequestType: is the object type being fetched from the API. In our case, the AstronomyPicture
 * acts as the RequestType.
 *
 * @query: is a lambda responsible for fetching a Flow of the ResultType from the local storage.
 *
 * @fetch: is a lambda responsible for fetching data of the RequestType from the API. It is
 * suspended as API calls can take a while to run.
 *
 * @saveFetchData: is a lambda responsible saving data fetched from the API to local storage. It
 * takes the RequestType as a parameter. It is suspended as saving data to local storage could be
 * long running.
 *
 * @shouldFetch: is a lambda responsible for checking to see if data in local storage is stale or
 * empty. If either one of these situations happens to be the case, an API call would be made to
 * get fresh data else no API call will be made.
 *
 * */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchData: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = {true}
) =
    flow {
        // Fetch data from local storage
        val data = query().first()

        val flow =
            // Check to see if data needs to be fetched from API
            if(shouldFetch(data)){

                // Emit resource state of loading while data is being fetched from API
            emit(Resource.Loading(data))

            try {
                // If data is successfully fetched from API save it to local storage
                saveFetchData(fetch())

                // Then fetch data from local storage and wrap it in a Resource object (Success in
                // this case)
                query().map {
                    result ->

                    Resource.Success(result)
                }
            } catch (e: Exception){
                // If fetching data from API fails, fetch data from local storage, wrap it in a
                    // Resource object (Failed in this case, alongside with the throwable)
                query().map {
                    result ->
                    Resource.Failed(e, result)
                }
            }
        } else {
            // If data does not need to be fetched from the API, then fetch data from local storage,
                //wrap it in a Resource object (Success in this case)
            query().map {
                result ->
                Resource.Success(result)
            }
        }

        // Emit the flow depending on the case that plays out.
        emitAll(flow)
    }