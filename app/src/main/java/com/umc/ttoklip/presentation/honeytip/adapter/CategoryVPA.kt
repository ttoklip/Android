package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.ShareHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.HouseWorkHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.RecipeHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.SafeLivingHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.honeytiplist.WelfareHoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.questionlist.HouseWorkQuestionListFragment
import com.umc.ttoklip.presentation.honeytip.questionlist.RecipeQuestionListFragment
import com.umc.ttoklip.presentation.honeytip.questionlist.SafeLivingQuestionListFragment
import com.umc.ttoklip.presentation.honeytip.questionlist.WelfareQuestionListFragment

class CategoryVPA(
    fragment: Fragment,
    private val caller: String,
    private val count: Int,
    ) : FragmentStateAdapter(fragment) {
    private val honeyTipFragmentList = listOf<Fragment>(
        HouseWorkHoneyTipListFragment(),
        RecipeHoneyTipListFragment(),
        SafeLivingHoneyTipListFragment(),
        WelfareHoneyTipListFragment()
    )

    private val questionFragmentList = listOf<Fragment>(
        HouseWorkQuestionListFragment(),
        RecipeQuestionListFragment(),
        SafeLivingQuestionListFragment(),
        WelfareQuestionListFragment()
    )
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return if(caller == HONEY_TIP) honeyTipFragmentList[position] else questionFragmentList[position]
    }
}