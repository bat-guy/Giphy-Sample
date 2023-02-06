package com.example.gifexample.util

import com.example.gifexample.model.GifEntity

interface GifItemClickListener {
    fun onFavouriteClicked(data: GifEntity)
}