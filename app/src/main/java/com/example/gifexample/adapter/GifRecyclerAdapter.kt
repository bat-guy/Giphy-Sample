package com.example.gifexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gifexample.util.Extensions.gone
import com.example.gifexample.util.Extensions.pulse
import com.example.gifexample.util.Extensions.px
import com.example.gifexample.util.Extensions.visible
import com.example.gifexample.util.GifEntityComparator
import com.example.gifexample.util.GifItemClickListener
import com.example.gifexample.util.GifListType
import com.example.gifexample.util.GifListType.*
import com.example.gifexample.R
import com.example.gifexample.databinding.ItemTrendingBinding
import com.example.gifexample.model.GifEntity


class GifRecyclerAdapter(private val listType: GifListType, private val listener: GifItemClickListener?) :
    PagingDataAdapter<GifEntity, RecyclerView.ViewHolder>(GifEntityComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (listType) {
            TRENDING_LIST,
            SEARCH_LIST -> TrendingViewHolder(ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FAVOURITE_LIST -> FavouriteViewHolder(ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            if (holder is TrendingViewHolder)
                holder.apply(item) {
                    listener?.let {
                        item.isFavourite = !item.isFavourite
                        it.onFavouriteClicked(item)
                        notifyItemChanged(position)
                    }
                }
            else (holder as FavouriteViewHolder).apply(item)
        }
    }

    class TrendingViewHolder(private val binding: ItemTrendingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun apply(data: GifEntity, listener: () -> Unit) {
            binding.apply {
                llFavourite.gone()
                Glide.with(ivGif.context)
                    .asGif()
                    .load(data.url)
                    .override(data.width.px, data.height.px)
                    .placeholder(R.drawable.loader)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .listener(object : RequestListener<GifDrawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {

                            return false
                        }

                        override fun onResourceReady(resource: GifDrawable?, model: Any?,
                                                     target: Target<GifDrawable>?, dataSource: DataSource?,
                                                     isFirstResource: Boolean): Boolean {
                            llFavourite.visible()
                            return false
                        }
                    })
                    .into(binding.ivGif)
                ivFavourite.apply {
                    setImageDrawable(ContextCompat.getDrawable(root.context, if (data.isFavourite) R.drawable.ic_favorite_24_red
                    else R.drawable.ic_favorite_border_24_red))
                    setOnClickListener {
                        pulse()
                        listener.invoke()
                    }
                }
            }
        }
    }

    inner class FavouriteViewHolder(private val binding: ItemTrendingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun apply(data: GifEntity) {
            binding.apply {
                Glide.with(ivGif.context)
                    .asGif()
                    .load(data.url)
                    .placeholder(R.drawable.loader)
                    .override(data.width.px, data.height.px)
                    .into(binding.ivGif)
                llFavourite.gone()
            }
        }
    }
}

