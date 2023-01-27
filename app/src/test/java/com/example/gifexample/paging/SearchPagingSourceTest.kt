package com.example.gifexample.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.gifexample.model.GifEntity
import com.example.gifexample.db.FavouriteDao
import com.example.gifexample.remote.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SearchPagingSourceTest {
    @Mock
    private lateinit var api: ApiService

    @Mock
    private lateinit var dao: FavouriteDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var pagingSource: SearchPagingSource

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pagingSource = SearchPagingSource("", api, dao)
    }

    @Test
    fun test_emptyResponse() = runTest {
        given(api.getSearchData("", 15, 0)).willReturn(null)
        given(dao.getFavouriteList()).willReturn(emptyList())
        val expectedResult = PagingSource.LoadResult.Page<Int, GifEntity>(
            data = listOf(),
            prevKey = null,
            nextKey = null
        )
        assertEquals(
            expectedResult.toString(), pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }
}