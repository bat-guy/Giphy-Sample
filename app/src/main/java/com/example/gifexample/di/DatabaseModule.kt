package com.example.gifexample.di

import com.example.gifexample.db.FavouriteDao
import com.example.gifexample.db.GifDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideFavouriteDao(appDatabase: GifDataBase): FavouriteDao {
        return appDatabase.favouriteDao()
    }
}