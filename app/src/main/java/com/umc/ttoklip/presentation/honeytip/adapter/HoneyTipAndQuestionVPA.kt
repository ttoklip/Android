package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.presentation.honeytip.ASK
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.QuestionFragment
import com.umc.ttoklip.presentation.honeytip.ShareHoneyTipFragment

class HoneyTipAndQuestionVPA(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    private var fragmentList = mutableListOf(
        HoneyTipFragment(),
        QuestionFragment()
    )

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}