package com.example.homework_repeat.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.homework_repeat.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this).load(url).placeholder(R.drawable.ic_person).into(this)
}