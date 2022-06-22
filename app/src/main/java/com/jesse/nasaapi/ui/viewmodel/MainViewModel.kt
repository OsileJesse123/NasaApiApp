package com.jesse.nasaapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesse.nasaapi.data.repository.AstronomyPictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val astronomyPictureRepository: AstronomyPictureRepository,
            ): ViewModel() {
    suspend fun getAstronomyPictures() = astronomyPictureRepository.getAstronomyPictures().stateIn(viewModelScope)
}