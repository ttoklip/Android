package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import android.util.Log
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentHoneyTipBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipAndQuestionVPA
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import com.umc.ttoklip.presentation.search2.SearchActivity2
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HoneyTipFragment : BaseFragment<FragmentHoneyTipBinding>(R.layout.fragment_honey_tip) {
    private var board = HONEY_TIPS
    private val viewModel: HoneyTipViewModel by viewModels()
    override fun initObserver() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHoneyTipMain()
    }

    override fun initView() {
        initTabLayout()
        goWriteActivity()
        binding.searchBtn.setOnSingleClickListener {
            startActivity(SearchActivity2.newIntent(requireContext()))
        }

        binding.alarmBtn.setOnSingleClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
    }

    private fun goWriteActivity() {
        binding.writeFab.setOnSingleClickListener {
            startActivity(WriteHoneyTipActivity.newIntent(requireContext(), board))
        }
    }

    private fun initTabLayout() {
        val tabTitles = listOf(HONEY_TIPS, ASK)
        binding.boardVp.apply {
            adapter = HoneyTipAndQuestionVPA(this@HoneyTipFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.boardTablayout, binding.boardVp) { tab, position ->
            for (i in tabTitles.indices) {
                tab.text = tabTitles[position]
            }
        }.attach()
        setBoard()
    }

    private fun setBoard() {
        binding.boardTablayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                board = tab?.text.toString()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}