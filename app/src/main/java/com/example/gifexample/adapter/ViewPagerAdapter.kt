package com.example.gifexample.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gifexample.ui.MainActivity

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragmentList: List<Fragment>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}