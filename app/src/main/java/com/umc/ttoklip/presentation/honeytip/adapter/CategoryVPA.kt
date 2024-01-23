package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.honeytip.HoneyTipListFragment

class CategoryVPA (fragment: Fragment, private val count: Int): FragmentStateAdapter(fragment) {
    /*private var fragmentList = for (i in 0..count){
        mutableListOf(ShareHoneyTipFragment(), HoneyTipListFragment())}*/
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return HoneyTipListFragment()
    }
}