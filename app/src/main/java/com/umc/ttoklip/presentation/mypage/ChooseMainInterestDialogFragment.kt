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
            noInterestChip.setOnClickListener {
                removeAllInterest()
            }
            houseworkChip.setOnClickListener {
                changeInterest(houseworkChip)
            }
            cookingChip.setOnClickListener {
                changeInterest(cookingChip)
            }
            safeLifeChip.setOnClickListener {
                changeInterest(safeLifeChip)
            }
            fraudChip.setOnClickListener {
                changeInterest(fraudChip)
            }
            welfarePolicyChip.setOnClickListener {
                changeInterest(welfarePolicyChip)
            }
            chooseBtn.setOnClickListener {
                if (interest.isNotEmpty()) {
                    btnClickListener(interest.toList())
                    dismiss()
                }
            }
        }
    }

    private fun changeInterest(chip: Chip) {
        Log.d("chip", chip.text.toString())
        if (binding.noInterestChip.tag == YES_INTERESTING) {
            removeInterest(binding.noInterestChip)
        }
        if (chip.tag == NOT_INTERESTING) {
            addInterest(chip)
        } else {
            removeInterest(chip)
        }
        checkInterest()
    }

    private fun removeAllInterest() {
        with(binding) {
            if (noInterestChip.tag == NOT_INTERESTING) {
                removeInterest(houseworkChip)
                removeInterest(cookingChip)
                removeInterest(safeLifeChip)
                removeInterest(fraudChip)
                removeInterest(welfarePolicyChip)
                addInterest(noInterestChip)
            } else {
                removeInterest(noInterestChip)
            }
        }
        checkInterest()
    }

    private fun addInterest(chip: Chip) {
        chip.tag = YES_INTERESTING
        chip.setChipBackgroundColorResource(R.color.yellow)
        chip.setChipStrokeColorResource(R.color.yellow)
        interest.add(chip.text.toString())
    }

    private fun removeInterest(chip: Chip) {
        chip.tag = NOT_INTERESTING
        chip.setChipBackgroundColorResource(R.color.white)
        chip.setChipStrokeColorResource(R.color.gray40)
        interest.remove(chip.text.toString())
    }

    private fun checkInterest() {
        with(binding.chooseBtn) {
            if (interest.isNotEmpty()) {
                setBackgroundResource(R.drawable.yellow_btn_background)
                setTextAppearance(R.style.TextAppearance_App_16sp_700)
            } else {
                setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                setTextAppearance(R.style.TextAppearance_App_16sp_500)
            }
        }

    }

    companion object {
        private const val NOT_INTERESTING = "NO"
        private const val YES_INTERESTING = "YES"
    }
}