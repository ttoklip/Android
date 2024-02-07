package com.umc.ttoklip.presentation.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.news.NewsFragment
import com.umc.ttoklip.presentation.news.fragment.HouseWorkFragment
import com.umc.ttoklip.presentation.news.fragment.RecipeFragment
import com.umc.ttoklip.presentation.news.fragment.SafeLifeFragment
import com.umc.ttoklip.presentation.news.fragment.WelfareFragment

class NewsTabAdapter(fragmentActivity: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentActivity, lifecycle) {

    private val fragmentList = mutableListOf<Fragment>(
        HouseWorkFragment(),
        RecipeFragment(),
        SafeLifeFragment(),
        WelfareFragment()

    )

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        return fragmentList[position]
    }

    fun addItem(fragment: Fragment) {
        fragmentList.add(fragment)
    }

}