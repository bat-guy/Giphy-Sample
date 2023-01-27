package com.example.gifexample.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gifexample.db.FavouriteDao
import com.example.gifexample.model.GifEntity
import com.example.gifexample.remote.ApiService
import java.io.IOException
import javax.inject.Inject

/**
 * Paging source class for search queries.
 */
class SearchPagingSource @Inject constructor(private val query: String, private val api: ApiService, private val dao: FavouriteDao) :
    PagingSource<Int, GifEntity>() {

    companion object {
        const val SEARCH_PAGE_LIMIT = 15
    }

    override fun getRefreshKey(state: PagingState<Int, GifEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifEntity> {
        val pageIndex = params.key ?: 1
        return try {
            val pagedResponse = if (query.isNotEmpty()) api.getSearchData(query, SEARCH_PAGE_LIMIT, SEARCH_PAGE_LIMIT * (pageIndex - 1)) else null
            val set = HashSet<String>().apply {
                dao.getFavouriteList().forEach {
                    add(it.giphyId)
                }
            }
            val data = pagedResponse?.let { GifEntity.convertList(it, set) }

            var nextPageNumber: Int? = null
            pagedResponse?.pagination?.let {
                nextPageNumber = if (!data.isNullOrEmpty() && it.totalCount != 0) pageIndex + 1 else null
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(IOException("Network not available, Please try again!"))
        } catch (exception: Exception) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}