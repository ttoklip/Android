package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.honeytip.ShareHoneyTipFragment

class HoneyTipAndQuestionVPA(fragment: Fragment): FragmentStateAdapter(fragment) {
    private var fragmentList = mutableListOf(ShareHoneyTipFragment(), ShareHoneyTipFragment())
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}