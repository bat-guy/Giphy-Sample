package com.example.gifexample.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.gifexample.db.FavouriteDao
import com.example.gifexample.model.GifEntity
import com.example.gifexample.paging.SearchPagingSource
import com.example.gifexample.paging.SearchPagingSource.Companion.SEARCH_PAGE_LIMIT
import com.example.gifexample.paging.TrendingPagingSource
import com.example.gifexample.paging.TrendingPagingSource.Companion.TRENDING_PAGE_LIMIT
import com.example.gifexample.remote.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyRepoImpl @Inject constructor(private val api: ApiService, private val dao: FavouriteDao) : MyRepo {

    override suspend fun addFavourite(data: GifEntity) {
        dao.insert(data)
    }

    override suspend fun removeFavourite(data: GifEntity) {
        dao.remove(data)
    }

    override fun getFavouriteList() = dao.getFavouriteListLive()

    override fun getSearchData(searchTerm: String): LiveData<PagingData<GifEntity>> = Pager(
        config = PagingConfig(pageSize = SEARCH_PAGE_LIMIT,
            enablePlaceholders = false,
            initialLoadSize = 2),
        pagingSourceFactory = { SearchPagingSource(searchTerm, api, dao) },
        initialKey = 1
    ).liveData

    override fun fetchTrendingList(): Flow<PagingData<GifEntity>> = Pager(
        config = PagingConfig(pageSize = TRENDING_PAGE_LIMIT,
            enablePlaceholders = false,
            initialLoadSize = 2),
        pagingSourceFactory = { TrendingPagingSource(api, dao) },
        initialKey = 1
    ).flow
}