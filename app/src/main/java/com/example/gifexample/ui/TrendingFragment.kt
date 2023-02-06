package com.example.gifexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gifexample.adapter.GifRecyclerAdapter
import com.example.gifexample.databinding.FragmentGifBinding
import com.example.gifexample.model.GifEntity
import com.example.gifexample.util.Extensions.gone
import com.example.gifexample.util.Extensions.visible
import com.example.gifexample.util.GifItemClickListener
import com.example.gifexample.util.GifListType
import com.example.gifexample.viewmodel.GifViewModel
import com.example.gifexample.viewmodel.GifViewModel.Companion.SEARCH
import com.example.gifexample.viewmodel.GifViewModel.Companion.TRENDING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment where Trending and Search results are shown.
 */
class TrendingFragment : Fragment(), GifItemClickListener {

    private lateinit var binding: FragmentGifBinding
    private lateinit var viewModel: GifViewModel
    private val trendingAdapter = GifRecyclerAdapter(GifListType.TRENDING_LIST, this)
    private val searchAdapter = GifRecyclerAdapter(GifListType.SEARCH_LIST, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[GifViewModel::class.java]
        viewModel.let {
            lifecycleScope.launchWhenCreated {
                it.typeLd.observe(viewLifecycleOwner) {
                    it?.let { setViewType(it) }
                }
                it.searchItems.observe(viewLifecycleOwner) {
                    it?.let { searchAdapter.submitData(lifecycle, it) }
                }
                it.trendingList?.collectLatest {
                    trendingAdapter.submitData(lifecycle, it)
                }
            }
        }
    }

    private fun initViews() {
        binding.apply {
            searchView.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.onSearch(newText)
                        return true
                    }
                })
                visible()
            }
            rvTrending.apply {
                setHasFixedSize(true)
                setLoaderState(searchAdapter)
                setLoaderState(trendingAdapter)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    /**
     * Method that switches up the recyclerView adapter based on the type
     */
    private fun setViewType(it: String) {
        binding.apply {
            when (it) {
                TRENDING -> rvTrending.adapter = trendingAdapter
                SEARCH -> rvTrending.adapter = searchAdapter
            }
        }
    }

    /**
     * Method that sets up the loader state for the
     */
    private fun setLoaderState(adapter: GifRecyclerAdapter) {
        lifecycleScope.launch(Dispatchers.Main) {
            adapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    binding.progressBar.visible()
                } else {
                    binding.progressBar.gone()
                    val errorState = when {
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }
                    errorState?.let {
                        Toast.makeText(requireContext(), it.error.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onFavouriteClicked(data: GifEntity) {
        viewModel.onFavouriteClicked(data)
    }
}