package com.jesse.nasaapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.nasaapi.data.repository.AstronomyPictureRepository
import com.jesse.nasaapi.domain.AstronomyPictureFormattedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val astronomyPictureRepository: AstronomyPictureRepository,
            ): ViewModel() {

    private val _astronomyPicturesFlow = MutableStateFlow(listOf<AstronomyPictureFormattedUseCase>())
    val astronomyPicturesFlow get() = _astronomyPicturesFlow.asStateFlow()

    private val _dataFetchingState: MutableStateFlow<DataFetchingState> =
        MutableStateFlow(DataFetchingState.LOADING)
    val dataFetchingState get() = _dataFetchingState

    val triggerRefreshAstronomyPictures = flow {
        emit(true)
    }


    private fun setAstronomyPictures(){
        viewModelScope.launch {
                _astronomyPicturesFlow.value = astronomyPictureRepository
                    .getAstronomyPictures().stateIn(this).value
        }
    }

    fun refreshAstronomyPictures(){
        _dataFetchingState.value = DataFetchingState.LOADING
        viewModelScope.launch {
            try {

               astronomyPictureRepository.refreshAstronomyPictures()
                _dataFetchingState.value = DataFetchingState.SUCCESSFUL
                setAstronomyPictures()

            } catch (e: Exception){

                e.printStackTrace()
                _dataFetchingState.value =
                    DataFetchingState.FAILED(e.message ?: "Unknown Error")
                setAstronomyPictures()

            }
        }
    }
}

sealed class DataFetchingState{
    object LOADING : DataFetchingState()
    object SUCCESSFUL: DataFetchingState()
    data class FAILED(var errorMessage: String): DataFetchingState()
}