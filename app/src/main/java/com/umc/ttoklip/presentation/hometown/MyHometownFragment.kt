package com.umc.ttoklip.presentation.hometown

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyHometownBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.mypage.SortSpinnerAdapter

class MyHometownFragment : BaseFragment<FragmentMyHometownBinding>(R.layout.fragment_my_hometown) {
    override fun initObserver() {

    }

    override fun initView() {
        val sortFilters = listOf(
            getString(R.string.sort_most_recent),
            getString(R.string.sort_popularity),
            getString(R.string.sort_most_comments),
            getString(R.string.sort_most_scrap)
        )
        binding.myHometownFilterSpinner.adapter =
            SortSpinnerAdapter(requireContext(), sortFilters)
        binding.myHometownFilterSpinner.setSelection(0)
    }
}