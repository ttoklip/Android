package com.umc.ttoklip.presentation.mypage

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityManageMyInfoBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageMyInfoActivity :
    BaseActivity<ActivityManageMyInfoBinding>(R.layout.activity_manage_my_info) {
    override fun initView() {
        initViewListener()
    }

    private fun initViewListener() {
        binding.manageMyInfoBackBtn.setOnClickListener {
            this@ManageMyInfoActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.root.setOnClickListener {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

        binding.inputIndependentCareerEt.setOnClickListener {
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                if (year != ZERO_CAREER && month != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_base_format, year, month)
                } else if (year != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_year_format, year)
                } else if (month != ZERO_CAREER) {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_month_format, month)
                } else {
                    binding.inputIndependentCareerEt.text =
                        getString(R.string.my_independent_career_month_format, ZERO_CAREER)
                }
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.mainInterestGroup.setOnClickListener {
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                binding.mainInterestGroup.removeAllViews()
                interests.forEach { interest ->
                    val chip = Chip(this)
                    chip.text = interest
                    chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
                    chip.setTextColor(ContextCompat.getColor(this, R.color.black))
                    chip.setChipBackgroundColorResource(R.color.yellow)
                    chip.setChipStrokeColorResource(R.color.yellow)
                    binding.mainInterestGroup.addView(chip)
                }
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    override fun initObserver() = Unit

    companion object {
        private const val ZERO_CAREER = 0
    }

}