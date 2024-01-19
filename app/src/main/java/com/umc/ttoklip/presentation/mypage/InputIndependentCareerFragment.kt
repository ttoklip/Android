package com.umc.ttoklip.presentation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umc.ttoklip.databinding.FragmentInputIndependentCareerBinding

class InputIndependentCareerFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentInputIndependentCareerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputIndependentCareerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}