package com.example.gifexample.di

import com.example.gifexample.repository.MyRepo
import com.example.gifexample.repository.MyRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMyRepo(api: MyRepoImpl): MyRepo

}