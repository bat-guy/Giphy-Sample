package com.example.gifexample.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gifexample.MockData.getFavouriteEntity
import com.example.gifexample.MockData.getNonFavouriteEntity
import com.example.gifexample.model.GifEntity
import com.example.gifexample.repository.MyRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GifViewModelTest {
    @Mock
    private lateinit var repo: MyRepo

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_onSearchFilled() {
        Mockito.`when`(repo.fetchTrendingList()).thenReturn(null)
        GifViewModel(repo).apply {
            onSearch("d")
            assertEquals(typeLd.value, GifViewModel.SEARCH)
            assertEquals(searchQuery.value, "d")
        }
    }

    @Test
    fun test_onSearchEmpty() {
        Mockito.`when`(repo.fetchTrendingList()).thenReturn(null)
        GifViewModel(repo).apply {
            onSearch("")
            assertEquals(typeLd.value, GifViewModel.TRENDING)
            assertEquals(searchQuery.value, "")
        }
    }

    @Test
    fun test_onFavouriteItemAdded() = runBlocking {
        val flow = flow {
            emit(listOf<GifEntity>(getFavouriteEntity()))
        }
        Mockito.`when`(repo.fetchTrendingList()).thenReturn(null)
        Mockito.`when`(repo.getFavouriteList()).thenReturn(flow)
        val viewModel = GifViewModel(repo)
        launch {
            viewModel.onFavouriteClicked(getNonFavouriteEntity())
        }
        var data: List<GifEntity>? = null
        viewModel.favouriteList.collectLatest {
            data = it
        }
        assertTrue(data != null)
        assertTrue(data?.size == 1)
    }

    @Test
    fun test_onFavouriteItemRemoved() = runBlocking {
        val flow = flow {
            emit(emptyList<GifEntity>())
        }
        Mockito.`when`(repo.getFavouriteList()).thenReturn(flow)
        Mockito.`when`(repo.fetchTrendingList()).thenReturn(null)
        val viewModel = GifViewModel(repo)
        launch {
            viewModel.onFavouriteClicked(getNonFavouriteEntity())
        }
        var data: List<GifEntity>? = null
        viewModel.favouriteList.collectLatest {
            data = it
        }
        assertTrue(data != null)
        assertTrue(data?.size == 0)
    }


}