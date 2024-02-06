package com.umc.ttoklip.presentation.intro

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
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
            0->{val spannable=SpannableStringBuilder("어려움과 도움을 함께\n독립생활의 꿀팁공유")
                spannable.setSpan(StyleSpan(Typeface.BOLD),11,22, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                binding.itemIntroTitleTv.text=spannable
                /**인앱화면 연결 필요**/}
            1->{val spannable=SpannableStringBuilder("자취에 도움되는\n최신뉴스를 한눈에")
                spannable.setSpan(StyleSpan(Typeface.BOLD),8,13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                binding.itemIntroTitleTv.text=spannable}
            else->{val spannable=SpannableStringBuilder("혼자서는 비싸,\n동네친구들과 함께")
                spannable.setSpan(StyleSpan(Typeface.BOLD),15,18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                binding.itemIntroTitleTv.text=spannable}
        }

        return binding.root
    }
}