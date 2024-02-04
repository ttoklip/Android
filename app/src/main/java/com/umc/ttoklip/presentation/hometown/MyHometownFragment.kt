package com.umc.ttoklip.presentation.hometown

import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentMyHometownBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.hometown.adapter.Together
import com.umc.ttoklip.presentation.hometown.adapter.TogetherAdapter
import com.umc.ttoklip.presentation.mypage.SortSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyHometownFragment : BaseFragment<FragmentMyHometownBinding>(R.layout.fragment_my_hometown) {
    private val togetherAdapter by lazy { TogetherAdapter() }
    private val communicationAdapter by lazy { TogetherAdapter() }
    override fun initObserver() {

    }

    override fun initView() {
        initDropdownSortFilter()
        initTogetherRv()
        initCommunicationRv()
    }

    private fun initCommunicationRv() {
        val togethers = listOf(
            Together("같이 햇반 대량 구매하실 분?", "상록구 해양동 한양대학로 12"),
            Together("같이 햇반 대량 구매하실 분?", "상록구 해양동 한양대학로 12"),
            Together("같이 햇반 대량 구매하실 분?", "상록구 해양동 한양대학로 12")
        )

        binding.communicationRv.apply {
            adapter = communicationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        communicationAdapter.submitList(togethers)
    }

    private fun initTogetherRv() {
        val togethers = listOf(
            Together("같이 햇반 대량 구매하실 분?", "상록구 해양동 한양대학로 12"),
            Together("같이 햇반 대량 구매하실 분?", "상록구 해양동 한양대학로 12"),
            Together("같이 햇반 대량 구매하실 분?", "상록구 해양동 한양대학로 12")
        )

        binding.togetherRv.apply {
            adapter = togetherAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        togetherAdapter.submitList(togethers)
    }

    private fun initDropdownSortFilter() {
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