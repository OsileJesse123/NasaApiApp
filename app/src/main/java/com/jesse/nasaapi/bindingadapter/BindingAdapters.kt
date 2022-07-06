package com.jesse.nasaapi.bindingadapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jesse.nasaapi.R

// This is used for 2 way binding inside the astronomy_picture_item_layout. It basically loads the
// image obtained from the image url into the given ImageView.
@BindingAdapter("imgUrl")
fun setAstronomyImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        Log.e("Glide", "Started")
        val imageUri = it.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
            .load(imageUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image)
            )
            .into(imageView)
    }
    Log.e("Glide", "Finished")
}

// This is used for 2 way binding inside the astronomy_picture_item_layout. It sets the text, in this
// case, the title of the astronomy picture being fetched into the given TextView.
@BindingAdapter("titleText")
fun setAstronomyText(textView: TextView, title: String?){
    title?.let{
        textView.text = it
    }
}