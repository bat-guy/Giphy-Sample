package com.example.gifexample.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.gifexample.model.GifEntity
import com.example.gifexample.repository.MyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifViewModel @Inject constructor(private val repo: MyRepo) : ViewModel() {

    companion object {
        const val TRENDING = "TRENDING"
        const val SEARCH = "SEARCH"
    }

    private val _searchQuery = MutableLiveData("")
    @VisibleForTesting
    val searchQuery: LiveData<String> = _searchQuery
    private val _typeLd = MutableLiveData<String>()
    val typeLd: LiveData<String> = _typeLd

    init {
        _typeLd.value = TRENDING
    }

    val searchItems = _searchQuery.switchMap {
        repo.getSearchData(it).cachedIn(viewModelScope)
    }

    val trendingList = repo.fetchTrendingList()?.cachedIn(viewModelScope)

    val favouriteList = repo.getFavouriteList()

    /**
     * Method called when favourite button is clicked.
     * Items are removed or added to to DB based upon isFavourite flag.
     */
    fun onFavouriteClicked(data: GifEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (data.isFavourite) {
                repo.addFavourite(data)
            } else {
                repo.removeFavourite(data)
            }
        }
    }

    /**
     * Method called when searchView contents are changed.
     * If data.isNullOrEmpty() _type -> TRENDING else SEARCH
     */
    fun onSearch(data: String?) {
        if (data != _searchQuery.value) {
            _typeLd.value = if (data.isNullOrEmpty().not()) SEARCH else TRENDING
            _searchQuery.value = data
        }
    }

}