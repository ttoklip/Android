package com.umc.ttoklip.presentation.mypage

import android.os.Build
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentInputIndependentCareerBinding
import com.umc.ttoklip.presentation.base.BaseBottomSheetDialogFragment
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputIndependentCareerDialogFragment(private val btnClickListener: (Int, Int) -> Unit) :
    BaseBottomSheetDialogFragment<FragmentInputIndependentCareerBinding>(R.layout.fragment_input_independent_career) {
    override fun initObserver() = Unit

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initView() {
        this@InputIndependentCareerDialogFragment.dialog?.window?.setLayout(
            WRAP_CONTENT,
            WRAP_CONTENT
        )
        with(binding.yearSelector) {
            minValue = PERIOD_START
            maxValue = YEAR_END
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            selectionDividerHeight = DIVIDER_HEIGHT_ZERO
        }
        with(binding.monthSelector) {
            minValue = PERIOD_START
            maxValue = MONTH_END
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            selectionDividerHeight = DIVIDER_HEIGHT_ZERO
        }
        binding.chooseBtn.setOnSingleClickListener {
            btnClickListener(binding.yearSelector.value, binding.monthSelector.value)
            this@InputIndependentCareerDialogFragment.dismiss()
        }
    }

    companion object {
        private const val PERIOD_START = 0
        private const val YEAR_END = 99
        private const val MONTH_END = 12
        private const val DIVIDER_HEIGHT_ZERO = 0
    }
}