package com.muhammed.noteapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.muhammed.noteapp.R

object GlideExtensions {
    private const val default = R.drawable.ic_no_image

    fun ImageView.loadWithUrl(imageUrl: String?) {
        Glide.with(this.context)
            .load(imageUrl)
            .error(default)
            .placeholder(default)
            .into(this)
    }
}