package com.example.gifexample.util

import androidx.recyclerview.widget.DiffUtil
import com.example.gifexample.model.GifEntity

internal object GifEntityComparator : DiffUtil.ItemCallback<GifEntity>() {
    override fun areItemsTheSame(oldItem: GifEntity, newItem: GifEntity) = oldItem.giphyId == newItem.giphyId

    override fun areContentsTheSame(oldItem: GifEntity, newItem: GifEntity) = oldItem == newItem
}