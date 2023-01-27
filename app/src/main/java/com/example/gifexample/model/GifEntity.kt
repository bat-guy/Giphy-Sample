package com.example.gifexample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favourite_table")
data class GifEntity(
    @ColumnInfo("id") val id: Long = Calendar.getInstance().timeInMillis,
    @PrimaryKey @ColumnInfo("giphyId") val giphyId: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("width") val width: Int,
    @ColumnInfo("height") val height: Int,
    @ColumnInfo("is_favourite") var isFavourite: Boolean,
) {
    companion object {
        /**
         * Method to convert the List<GiphyData> fetch from Server to List<GifEntity>.
         * Additionally, a set of String containing 'giphyId' of favourite items is also accepted.
         */
        fun convertList(response: ResponseGiphyList, set: HashSet<String>): List<GifEntity> {
            val dataList = arrayListOf<GifEntity>()
            response.data?.forEach {
                dataList.add(GifEntity(giphyId = it.id, url = it.images.downsized.url,
                    width = it.images.downsized.width,
                    height = it.images.downsized.height,
                    isFavourite = set.contains(it.id)))
            }
            return dataList
        }
    }
}
