package com.jesse.nasaapi.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.nasaapi.data.database.AstronomyPicture
import com.jesse.nasaapi.repository.AstronomyPictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val astronomyPictureRepository: AstronomyPictureRepository): ViewModel() {

    private val _astronomyPicturesFlow = MutableStateFlow(listOf<AstronomyPicture>())
    val astronomyPicturesFlow get() = _astronomyPicturesFlow.asStateFlow()

    private val _dataFetchingState: MutableStateFlow<DataFetchingState> =
        MutableStateFlow(DataFetchingState.LOADING)
    val dataFetchingState get() = _dataFetchingState

    val setAstronomyPicturesTriggerFlow = flow {
        emit(true)
    }
    fun setAstronomyPictures(){
        _dataFetchingState.value = DataFetchingState.LOADING
        viewModelScope.launch {
            try {
                _astronomyPicturesFlow.value = astronomyPictureRepository
                    .getAstronomyPictures().stateIn(this).value
                        _dataFetchingState.value = DataFetchingState.SUCCESSFUL
                        } catch (e: Exception){
                _dataFetchingState.value =
                    DataFetchingState.FAILED(e.message ?: "Unknown Error")
            }
        }
    }

    fun refreshAstronomyPictures(){
        _dataFetchingState.value = DataFetchingState.LOADING
        viewModelScope.launch {
            try {
               astronomyPictureRepository.refreshAstronomyPictures()
                _dataFetchingState.value = DataFetchingState.SUCCESSFUL
            } catch (e: Exception){
                e.printStackTrace()
                _dataFetchingState.value =
                    DataFetchingState.FAILED(e.message ?: "Unknown Error")
            }
        }
    }
}

sealed class DataFetchingState{
    object LOADING : DataFetchingState()
    object SUCCESSFUL: DataFetchingState()
    data class FAILED(var errorMessage: String): DataFetchingState()
}