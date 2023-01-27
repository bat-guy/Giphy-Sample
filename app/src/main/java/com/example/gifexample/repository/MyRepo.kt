package com.example.gifexample.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.gifexample.model.GifEntity
import kotlinx.coroutines.flow.Flow

interface MyRepo {
    fun getSearchData(searchTerm: String): LiveData<PagingData<GifEntity>>
    fun getFavouriteList(): Flow<List<GifEntity>>
    suspend fun addFavourite(data: GifEntity)
    suspend fun removeFavourite(data: GifEntity)
    fun fetchTrendingList(): Flow<PagingData<GifEntity>>?
}