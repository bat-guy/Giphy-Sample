package com.example.gifexample.remote

import com.example.gifexample.model.ResponseGiphyList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/gifs/trending")
    suspend fun getTrendingData(@Query("limit") limit: Int, @Query("offset") offset: Int): ResponseGiphyList?

    @GET("v1/gifs/search")
    suspend fun getSearchData(@Query("q") searchTerm: String, @Query("limit") limit: Int, @Query("offset") offset: Int): ResponseGiphyList?
}