package com.example.gifexample

import com.example.gifexample.model.GifEntity
import com.example.gifexample.model.*

object MockData {
    fun getFavouriteEntity() = GifEntity(giphyId = "7Ae6u4cMbO33vKKn28",
        url = "https://media1.giphy.com/media/7Ae6u4cMbO33vKKn28/200_d.gif?cid=1380c6c31v6envffod89b0zqoqst8u0peog2pwblsjcmcffk&rid=200_d.gif&ct=g",
        width = 356,
        height = 200,
        isFavourite = true)

    fun getNonFavouriteEntity() = GifEntity(giphyId = "7Ae6u4cMbO33vKKn281",
        url = "https://media1.giphy.com/media/7Ae6u4cMbO33vKKn28/200_d.gif?cid=1380c6c31v6envffod89b0zqoqst8u0peog2pwblsjcmcffk&rid=200_d.gif&ct=g",
        width = 356,
        height = 200,
        isFavourite = false)

    fun getSearchResponse() = ResponseGiphyList(listOf(GiphyData(type = "gif",
        id = "7Ae6u4cMbO33vKKn28",
        url = "https://media1.giphy.com/media/7Ae6u4cMbO33vKKn28/200_d.gif?cid=1380c6c31v6envffod89b0zqoqst8u0peog2pwblsjcmcffk&rid=200_d.gif&ct=g",
        Images(Downsized(width = 356,
            height = 200,
            url = "https://media1.giphy.com/media/7Ae6u4cMbO33vKKn28/200_d.gif?cid=1380c6c31v6envffod89b0zqoqst8u0peog2pwblsjcmcffk&rid=200_d.gif&ct=g")))),
        Pagination(0, 1, 0),
        Meta(200, "", "34567uijhnbv"))
}