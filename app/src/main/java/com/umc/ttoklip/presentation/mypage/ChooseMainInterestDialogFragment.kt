package com.umc.ttoklip.presentation.mypage

import androidx.core.content.ContextCompat
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentChooseMainInterestBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseMainInterestDialogFragment(private val btnClickListener: (List<String>) -> Unit) :
    BaseDialogFragment<FragmentChooseMainInterestBinding>(R.layout.fragment_choose_main_interest) {
    private val interest = ArrayList<String>()
    override fun initObserver() = Unit

    override fun initView() {
        binding.any.setOnClickListener {
            if (it.tag == NOT_INTERESTING) {
                it.tag = YES_INTERESTING
                interest.add(binding.any.text.toString())
                it.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.yellow_btn_background)
            } else {
                it.tag = NOT_INTERESTING
                interest.remove(binding.any.text.toString())
                it.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.no_interest_background)
            }
        }
        binding.chooseBtn.setOnClickListener {
            btnClickListener(interest.toList())
            dismiss()
        }
    }

    companion object {
        private const val NOT_INTERESTING = "NO"
        private const val YES_INTERESTING = "YES"
    }
}