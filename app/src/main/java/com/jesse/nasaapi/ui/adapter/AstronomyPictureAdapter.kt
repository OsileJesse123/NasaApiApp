package com.jesse.nasaapi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesse.nasaapi.data.database.model.AstronomyPicture
import com.jesse.nasaapi.databinding.AstronomyPictureItemLayoutBinding
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AstronomyPictureAdapter @Inject constructor():
    ListAdapter<AstronomyPicture, AstronomyPictureAdapter.AstronomyPictureViewHolder>(
        AstronomyPictureDiffCallback()) {

    inner class AstronomyPictureViewHolder(private val binding: AstronomyPictureItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bindData(astronomyPicture: AstronomyPicture) {
            binding.astronomyPicture = astronomyPicture
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

class AstronomyPictureDiffCallback(): DiffUtil.ItemCallback<AstronomyPicture>(){

    override fun areItemsTheSame(oldItem: AstronomyPicture, newItem: AstronomyPicture): Boolean {
        return oldItem.url == oldItem.url
    }

    override fun areContentsTheSame(oldItem: AstronomyPicture, newItem: AstronomyPicture): Boolean {
        return oldItem == newItem
    }

}