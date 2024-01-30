package com.umc.ttoklip.presentation.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.ttoklip.databinding.FragmentIntroBinding

class IntroFragment(val position:Int): Fragment() {
    private lateinit var binding: FragmentIntroBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentIntroBinding.inflate(inflater,container,false)

        when (position){
            0->{binding.itemIntroTitleTv.text="우리, 꿀팁 공유해요!"
                binding.itemIntroDescTv.text="독립생활을 하는데 어려운 점을 공유하고\n도움을 주며 해결해봐요"
                /**인앱화면 연결 필요**/}
            1->{binding.itemIntroTitleTv.text="오늘은 또 어떤 정보가?"
                binding.itemIntroDescTv.text="독립생활을 하는데 필요한 유용한\n정보들을 받아봐요"}
            else->{binding.itemIntroTitleTv.text="우리 동네 안에서 서로서로"
                binding.itemIntroDescTv.text="우리 동네 똑립이들과 친해지고, 공구하고,\n음식 배달비도 아껴봐요"}
        }

        return binding.root
    }
}