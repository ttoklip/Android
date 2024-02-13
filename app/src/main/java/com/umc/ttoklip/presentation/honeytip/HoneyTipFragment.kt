package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
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
import kotlinx.coroutines.launch


class HoneyTipFragment: BaseFragment<FragmentHoneyTipBinding>(R.layout.fragment_honey_tip) {
    private var board = HONEY_TIP
    private val viewModel: HoneyTipViewModel by activityViewModels()
    override fun initObserver() {
        viewModel.getHoneyTipMain()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.honeyTipMainEvent.collect{
                    viewModel.getHoneyTipMain()
                }
            }
        }

        viewModel.honeyTipMainData.observe(viewLifecycleOwner){
            initTabLayout()
        }
    }

    override fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        //viewModel.getHoneyTipMain()
        //initTabLayout()
        goWriteActivity()
        binding.searchBtn.setOnClickListener {
            startActivity(SearchActivity.newIntent(requireContext()))
        }

        binding.alarmBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }
    }

    private fun goWriteActivity(){
        binding.writeFab.setOnClickListener {
            val intent = Intent(activity, WriteHoneyTipActivity::class.java)
            board = viewModel.boardLiveData.value ?: ""
            intent.putExtra(BOARD, board)
            Log.d("GoWriteHoneyTip", board)
            startActivity(intent)
        }
    }

    private fun initTabLayout(){
        val tabTitles = listOf(HONEY_TIP, ASK)
        binding.boardVp.adapter = HoneyTipAndQuestionVPA(this)
        binding.boardVp.isUserInputEnabled = false
        Log.d("position", binding.boardTablayout.selectedTabPosition.toString())

        TabLayoutMediator(binding.boardTablayout, binding.boardVp){ tab, position ->
            for (i in tabTitles.indices){
                tab.text = tabTitles[position]
            }
        }.attach()
        setBoard()
    }

    private fun setBoard(){
        binding.boardTablayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                board = tab?.text.toString()
                viewModel.setBoardLiveData(board)
                Log.d("HoneyTipFragment", board)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}