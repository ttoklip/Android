package com.umc.ttoklip.presentation.news.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.news.ItemNewsFragment
import com.umc.ttoklip.presentation.news.NewsFragment

class NewsTabAdapter(fragmentActivity: NewsFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ItemNewsFragment()
            1 -> ItemNewsFragment()
            2 -> ItemNewsFragment()
            3 -> ItemNewsFragment()
            else -> ItemNewsFragment()
        }
    }
}