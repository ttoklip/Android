package com.umc.ttoklip.presentation.mypage

import android.widget.NumberPicker
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentInputIndependentCareerBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputIndependentCareerDialogFragment(private val btnClickListener: (Int, Int) -> Unit) :
    BaseDialogFragment<FragmentInputIndependentCareerBinding>(R.layout.fragment_input_independent_career) {
    override fun initObserver() = Unit

    override fun initView() {
        with(binding.yearSelector) {
            minValue = 0
            maxValue = 99
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        with(binding.monthSelector) {
            minValue = 0
            maxValue = 11
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        binding.chooseBtn.setOnClickListener {
            btnClickListener(binding.yearSelector.value, binding.monthSelector.value)
            this@InputIndependentCareerDialogFragment.dismiss()
        }
    }
}