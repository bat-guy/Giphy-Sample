package com.example.gifexample.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gifexample.MockData
import com.example.gifexample.db.FavouriteDao
import com.example.gifexample.model.GifEntity
import com.example.gifexample.remote.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MyRepoImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dao: FavouriteDao

    @Mock
    private lateinit var api: ApiService

    private lateinit var repo: MyRepoImpl

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repo = MyRepoImpl(api, dao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_emptyFavouriteList() = runTest {
        val flow = flow {
            emit(emptyList<GifEntity>())
        }
        Mockito.`when`(dao.getFavouriteListLive()).thenReturn(flow)
        var data: List<GifEntity>? = null
        repo.getFavouriteList().collectLatest {
            data = it
        }
        Assert.assertTrue(data != null)
        Assert.assertTrue(data?.size == 0)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_filledFavouriteList() = runTest {
        val flow = flow {
            emit(listOf<GifEntity>(MockData.getFavouriteEntity()))
        }
        Mockito.`when`(dao.getFavouriteListLive()).thenReturn(flow)
        var data: List<GifEntity>? = null
        repo.getFavouriteList().collectLatest {
            data = it
        }
        Assert.assertTrue(data != null)
        Assert.assertTrue(data?.size != 0)
    }


}