package com.jesse.nasaapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesse.nasaapi.databinding.AstronomyPictureItemLayoutBinding
import com.jesse.nasaapi.data.model.AstronomyPictureFormattedUseCase
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AstronomyPictureAdapter @Inject constructor():
    ListAdapter<AstronomyPictureFormattedUseCase, AstronomyPictureAdapter.AstronomyPictureViewHolder>(
        AstronomyPictureDiffCallback()) {

    inner class AstronomyPictureViewHolder(private val binding: AstronomyPictureItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bindData(astronomyPicture: AstronomyPictureFormattedUseCase) {
            binding.astronomyPictureFormatted = astronomyPicture
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AstronomyPictureViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AstronomyPictureItemLayoutBinding.inflate(layoutInflater, parent,
            false)
        return AstronomyPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AstronomyPictureViewHolder, position: Int) {
       holder.bindData(currentList[position])
    }
}

class AstronomyPictureDiffCallback : DiffUtil.ItemCallback<AstronomyPictureFormattedUseCase>(){

    override fun areItemsTheSame(oldItem: AstronomyPictureFormattedUseCase, newItem: AstronomyPictureFormattedUseCase): Boolean {
        return oldItem.hdUrl == oldItem.hdUrl
    }

    override fun areContentsTheSame(oldItem: AstronomyPictureFormattedUseCase, newItem: AstronomyPictureFormattedUseCase): Boolean {
        return oldItem == newItem
    }

}