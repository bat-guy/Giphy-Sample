package com.example.gifexample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gifexample.model.GifEntity

@Database(entities = [GifEntity::class], version = 1)
abstract class GifDataBase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}