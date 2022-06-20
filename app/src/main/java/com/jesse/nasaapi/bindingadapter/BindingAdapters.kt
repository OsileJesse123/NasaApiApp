package com.jesse.nasaapi.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jesse.nasaapi.R


@BindingAdapter("imgUrl")
fun setAstronomyImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        val imageUri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
            .load(imageUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image)
            )
            .into(imageView)
    }
}

@BindingAdapter("titleText")
fun setAstronomyText(textView: TextView, title: String?){
    title?.let{
        textView.text = it
    }
}