package com.umc.ttoklip.presentation.honeytip.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.ttoklip.data.model.honeytip.HoneyTipCategory
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.HoneyTipListFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity

class CategoryVPA(
    fragment: Fragment,
    private val count: Int,
    ) : FragmentStateAdapter(fragment) {
    /*private var fragmentList = for (i in 0..count){
        mutableListOf(ShareHoneyTipFragment(), HoneyTipListFragment())}*/
    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return HoneyTipListFragment()
        /*return when (position) {
            0 -> HoneyTipListFragment(board, WriteHoneyTipActivity.Category.HOUSEWORK.toString())
            1 -> HoneyTipListFragment(board, WriteHoneyTipActivity.Category.RECIPE.toString())
            2 -> HoneyTipListFragment(board, WriteHoneyTipActivity.Category.SAFE_LIVING.toString())
            else -> HoneyTipListFragment(board, WriteHoneyTipActivity.Category.WELFARE_POLICY.toString())
        }*/
    }
}