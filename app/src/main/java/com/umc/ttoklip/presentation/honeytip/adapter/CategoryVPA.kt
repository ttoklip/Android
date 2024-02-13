package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.honeytip.HoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.ShareHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.HouseWorkHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.RecipeHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.SafeLivingHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.WelfareHoneyTipListFragment

class CategoryVPA(
    fragment: Fragment,
    private val count: Int,
    ) : FragmentStateAdapter(fragment) {
    private var fragmentList = listOf<Fragment>(
        HouseWorkHoneyTipListFragment(),
        RecipeHoneyTipListFragment(),
        SafeLivingHoneyTipListFragment(),
        WelfareHoneyTipListFragment()
    )
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}