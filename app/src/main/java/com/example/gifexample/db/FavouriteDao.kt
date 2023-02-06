package com.example.gifexample.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.gifexample.model.GifEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: GifEntity)

    @Delete
    fun remove(product: GifEntity)

    @Query("SELECT * FROM favourite_table ORDER BY id DESC")
    fun getFavouriteListLive(): Flow<List<GifEntity>>

    @Query("SELECT * from favourite_table")
    suspend fun getFavouriteList(): List<GifEntity>

    @Query("SELECT * FROM favourite_table ORDER BY id DESC")
    fun fetchFavouriteList(): PagingSource<Int, GifEntity>
}