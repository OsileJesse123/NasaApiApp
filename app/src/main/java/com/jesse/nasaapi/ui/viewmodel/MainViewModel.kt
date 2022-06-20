package com.jesse.nasaapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.nasaapi.data.repository.AstronomyPictureRepository
import com.jesse.nasaapi.domain.AstronomyPictureFormattedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val astronomyPictureRepository: AstronomyPictureRepository,
            ): ViewModel() {

    private val _astronomyPicturesFlow = MutableStateFlow(listOf<AstronomyPictureFormattedUseCase>())
    val astronomyPicturesFlow get() = _astronomyPicturesFlow.asStateFlow()

    private val _triggerRefreshAstronomyPictures = Channel<DataFetchingState>(Channel.CONFLATED)
    val triggerRefreshAstronomyPictures = _triggerRefreshAstronomyPictures.receiveAsFlow()


    fun triggerRefresh() = viewModelScope.launch {refreshAstronomyPictures() }

    private fun setAstronomyPictures(){
        viewModelScope.launch {
                _astronomyPicturesFlow.value = astronomyPictureRepository
                    .getAstronomyPictures().stateIn(this).value
        }
    }

    private fun refreshAstronomyPictures(){
        viewModelScope.launch {
            if (!_triggerRefreshAstronomyPictures.isClosedForSend)
                _triggerRefreshAstronomyPictures.send(DataFetchingState.LOADING)
            try {
                if (!_triggerRefreshAstronomyPictures.isClosedForSend){
                    astronomyPictureRepository.refreshAstronomyPictures()
                    _triggerRefreshAstronomyPictures.send(DataFetchingState.SUCCESSFUL)
                    setAstronomyPictures()
                }
            } catch (e: Exception){
                if(!_triggerRefreshAstronomyPictures.isClosedForSend){
                    setAstronomyPictures()
                    _triggerRefreshAstronomyPictures.send(DataFetchingState.FAILED(e.message ?: "Unknown Error"))
                }
            } finally {
                _triggerRefreshAstronomyPictures.close()
            }
        }
    }
}

sealed class DataFetchingState{
    object LOADING : DataFetchingState()
    object SUCCESSFUL: DataFetchingState()
    data class FAILED(var errorMessage: String): DataFetchingState()
}