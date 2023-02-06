package com.example.gifexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gifexample.util.Extensions.gone
import com.example.gifexample.adapter.GifRecyclerAdapter
import com.example.gifexample.databinding.FragmentGifBinding
import com.example.gifexample.util.GifListType
import com.example.gifexample.viewmodel.GifViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentGifBinding
    private lateinit var viewModel: GifViewModel
    private val rvAdapter = GifRecyclerAdapter(GifListType.FAVOURITE_LIST, null)

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
        lifecycleScope.launch {
            viewModel.favouriteList.collectLatest {
                rvAdapter.submitData(it)
            }
        }
    }

    private fun initViews() {
        binding.apply {
            rvTrending.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = rvAdapter
            }
            searchView.gone()
        }
    }
}