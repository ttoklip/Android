package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.ttoklip.databinding.FragmentHoneyTipBinding
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipAndQuestionVPA
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity


class HoneyTipFragment: Fragment() {
    private lateinit var binding: FragmentHoneyTipBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoneyTipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        binding.writeFab.setOnClickListener {
            val intent = Intent(activity, WriteHoneyTipActivity::class.java)
            intent.putExtra("caller", "writeFab")
            startActivity(intent)
        }
    }
    private fun initTabLayout(){
        val tabTitles = listOf("꿀팁 공유", "애스크")
        binding.boardVp.adapter = HoneyTipAndQuestionVPA(this)
        binding.boardVp.isUserInputEnabled = false

        TabLayoutMediator(binding.boardTablayout, binding.boardVp){ tab, position ->
            for (i in tabTitles.indices){
                tab.text = tabTitles[position]
            }
        }.attach()
    }
}