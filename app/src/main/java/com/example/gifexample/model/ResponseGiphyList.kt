package com.example.gifexample.model

import com.google.gson.annotations.SerializedName

/**
 * Response object for the the Giphy APIs.
 * Only Keeping the relevant object in the response for now.
 */
data class ResponseGiphyList(
    @SerializedName("data") val data: List<GiphyData>?,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("meta") val meta: Meta,
)

data class GiphyData(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("images") var images: Images
)

data class Images(
    @SerializedName("fixed_height_downsampled") var downsized: Downsized
)

data class Downsized(
    @SerializedName("url") var url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
)

data class Pagination(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int
)

data class Meta(
    @SerializedName("status") val status: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("response_id") val responseId: String
)