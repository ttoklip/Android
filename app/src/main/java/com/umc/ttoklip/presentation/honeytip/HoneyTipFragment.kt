package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipAndQuestionVPA
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity


class HoneyTipFragment: BaseFragment<FragmentHoneyTipBinding>(R.layout.fragment_honey_tip) {
    private var board = 0
    override fun initObserver() {

    }

    override fun initView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        view
        binding.writeFab.setOnClickListener {
            val intent = Intent(activity, WriteHoneyTipActivity::class.java)
            intent.putExtra("caller", "writeFab")
            startActivity(intent)
        }
    }
    private fun initTabLayout(){
        val tabTitles = listOf("꿀팁 공유", "질문해요")
        binding.boardVp.adapter = HoneyTipAndQuestionVPA(this)
        binding.boardVp.isUserInputEnabled = false

        TabLayoutMediator(binding.boardTablayout, binding.boardVp){ tab, position ->
            for (i in tabTitles.indices){
                tab.text = tabTitles[position]
            }
        }.attach()
        binding.boardTablayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
    fun updateBoard(){
        board = binding.boardTablayout.selectedTabPosition
        Log.d("board", board.toString())
    }
}