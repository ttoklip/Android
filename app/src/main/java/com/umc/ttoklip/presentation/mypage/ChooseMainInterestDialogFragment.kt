package com.umc.ttoklip.presentation.mypage

import android.util.Log
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentChooseMainInterestBinding
import com.umc.ttoklip.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseMainInterestDialogFragment(private val btnClickListener: (List<String>) -> Unit) :
    BaseBottomSheetDialogFragment<FragmentChooseMainInterestBinding>(R.layout.fragment_choose_main_interest) {
    private val interest = ArrayList<String>()
    override fun initObserver() = Unit

    override fun initView() {
        with(binding) {
            with(binding) {
                houseworkChip.setOnClickListener {
                    changeChip(houseworkChip)
                }
                cookingChip.setOnClickListener {
                    changeChip(cookingChip)
                }
                safeLifeChip.setOnClickListener {
                    changeChip(safeLifeChip)
                }
                fraudChip.setOnClickListener {
                    changeChip(fraudChip)
                }
                welfarePolicyChip.setOnClickListener {
                    changeChip(welfarePolicyChip)
                }
            }
            chooseBtn.setOnClickListener {
                if (interest.isNotEmpty()) {
                    btnClickListener(interest.toList())
                    dismiss()
                }
            }
        }
    }

    private fun changeChip(chip: Chip) {
        Log.d("chip", chip.text.toString())
        if (chip.tag == NOT_INTERESTING) {
            chip.tag = YES_INTERESTING
            chip.setChipBackgroundColorResource(R.color.yellow)
            chip.setChipStrokeColorResource(R.color.yellow)
            interest.add(chip.text.toString())
        } else {
            chip.tag = NOT_INTERESTING
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setChipStrokeColorResource(R.color.gray40)
            interest.remove(chip.text.toString())
        }
    }

    companion object {
        private const val NOT_INTERESTING = "NO"
        private const val YES_INTERESTING = "YES"
    }
}