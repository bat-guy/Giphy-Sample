package com.example.gifexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifexample.Extensions.gone
import com.example.gifexample.Extensions.px
import com.example.gifexample.R
import com.example.gifexample.databinding.ItemTrendingBinding
import com.example.gifexample.model.GifEntity

class FavouriteRecyclerAdapter() : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    private val trendingList = ArrayList<GifEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        trendingList[position].let { item ->
            holder.apply(item)
        }
    }

    override fun getItemCount() = trendingList.size

    fun setData(list: List<GifEntity>) {
        trendingList.clear()
        trendingList.addAll(list)
        notifyDataSetChanged()
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

