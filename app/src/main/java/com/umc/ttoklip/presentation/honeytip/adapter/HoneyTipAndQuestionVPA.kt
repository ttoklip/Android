package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.honeytip.InnerHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.QuestionFragment

class HoneyTipAndQuestionVPA(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    private var fragmentList = mutableListOf(
        InnerHoneyTipFragment(),
        QuestionFragment()
    )

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}