package com.example.gifexample.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.gifexample.R
import com.example.gifexample.adapter.ViewPagerAdapter
import com.example.gifexample.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        //Setting up the viewPager adapter with fragment list
        val fragmentList = listOf(TrendingFragment(), FavouriteFragment())
        binding.viewPager.adapter = ViewPagerAdapter(this@MainActivity, fragmentList)
        TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.trending)
                1 -> tab.text = getString(R.string.favourite)
            }
        }.attach()
    }

}