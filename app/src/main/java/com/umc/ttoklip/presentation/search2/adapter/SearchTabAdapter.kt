package com.umc.ttoklip.presentation.search2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.news.NewsFragment
import com.umc.ttoklip.presentation.news.fragment.HouseWorkFragment
import com.umc.ttoklip.presentation.news.fragment.RecipeFragment
import com.umc.ttoklip.presentation.news.fragment.SafeLifeFragment
import com.umc.ttoklip.presentation.news.fragment.WelfareFragment
import com.umc.ttoklip.presentation.search2.SearchActivity2
import com.umc.ttoklip.presentation.search2.fragment.SearchNewsFragment
import com.umc.ttoklip.presentation.search2.fragment.SearchTipFragment
import com.umc.ttoklip.presentation.search2.fragment.SearchTownFragment

class SearchTabAdapter(fragmentActivity: SearchActivity2) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = mutableListOf<Fragment>(
        SearchTipFragment(),
        SearchNewsFragment(),
        SearchTownFragment()
    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}