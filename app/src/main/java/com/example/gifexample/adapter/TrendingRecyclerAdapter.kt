package com.example.gifexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gifexample.Extensions.gone
import com.example.gifexample.Extensions.pulse
import com.example.gifexample.Extensions.px
import com.example.gifexample.Extensions.visible
import com.example.gifexample.R
import com.example.gifexample.databinding.ItemTrendingBinding
import com.example.gifexample.model.GifEntity


class TrendingRecyclerAdapter(private val listener: GifItemClickListener?) :
    PagingDataAdapter<GifEntity, TrendingRecyclerAdapter.TrendingViewHolder>(TrendingEntityComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.apply(item) {
                listener?.let {
                    item.isFavourite = !item.isFavourite
                    it.onFavouriteClicked(item)
                    notifyItemChanged(position)
                }
            }
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
}


object TrendingEntityComparator : DiffUtil.ItemCallback<GifEntity>() {
    override fun areItemsTheSame(oldItem: GifEntity, newItem: GifEntity) = oldItem.giphyId == newItem.giphyId

    override fun areContentsTheSame(oldItem: GifEntity, newItem: GifEntity) = oldItem == newItem
}

interface GifItemClickListener {
    fun onFavouriteClicked(data: GifEntity)
}