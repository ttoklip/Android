package com.umc.ttoklip.presentation.signup.fragments

import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup4Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.mypage.ChooseMainInterestDialogFragment
import com.umc.ttoklip.presentation.mypage.InputIndependentCareerDialogFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup4Fragment : BaseFragment<FragmentSignup4Binding>(R.layout.fragment_signup4) {
    private var independentCareerYear:Int? =null
    private var independentCareerMonth: Int? =null

    override fun initObserver() {
    }

    override fun initView() {
        val activity = activity as SignupActivity
        activity?.setProg(2)

        if(independentCareerYear!=null||independentCareerMonth!=null){
            if (independentCareerYear != ZERO_CAREER && independentCareerMonth != ZERO_CAREER) {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_base_format, independentCareerYear, independentCareerMonth)
            } else if (independentCareerYear != ZERO_CAREER) {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_year_format, independentCareerYear)
            } else if (independentCareerMonth != ZERO_CAREER) {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_month_format, independentCareerMonth)
            } else {
                binding.signup4IndependenceEt.text =
                    getString(R.string.my_independent_career_month_format, ZERO_CAREER)
            }
            binding.signup4IndependenceEt.setTextColor(ContextCompat.getColor(activity,R.color.black))
        }

        binding.signup4NickcheckButton.setOnClickListener {
            //닉네임 확인 로직
            if (binding.signup4NicknameEt.text.toString().equals("aa")) {
                binding.signup4NickokTv.visibility = View.VISIBLE
                binding.signup4NicknotokTv.visibility = View.GONE
            } else {
                binding.signup4NickokTv.visibility = View.GONE
                binding.signup4NicknotokTv.visibility = View.VISIBLE
            }
        }

        binding.signup4IndependenceEt.setOnClickListener {
            val bottomSheet = InputIndependentCareerDialogFragment { year, month ->
                if (year != ZERO_CAREER && month != ZERO_CAREER) {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_base_format, year, month)
                } else if (year != ZERO_CAREER) {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_year_format, year)
                } else if (month != ZERO_CAREER) {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_month_format, month)
                } else {
                    binding.signup4IndependenceEt.text =
                        getString(R.string.my_independent_career_month_format, ZERO_CAREER)
                }
                independentCareerYear=year
                independentCareerMonth=month
            }
            bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            binding.signup4IndependenceEt.setTextColor(ContextCompat.getColor(activity,R.color.black)
            )
        }

        binding.signup4InterestGroup.setOnClickListener {
            binding.signup4InterestDescTv.visibility = View.GONE
            binding.signup4InterestIconIv.visibility = View.GONE
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                binding.signup4InterestGroup.removeAllViews()
                interests.forEach { interest ->
                    val chip = Chip(activity)
                    chip.text = interest
                    chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
                    chip.setTextColor(ContextCompat.getColor(activity, R.color.black))
                    chip.setChipBackgroundColorResource(R.color.yellow)
                    chip.setChipStrokeColorResource(R.color.yellow)
                    binding.signup4InterestGroup.addView(chip)
                }
            }
            bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
        }

        binding.signup4NextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signup4_fragment_to_signup5_fragment)
        }
    }

    companion object {
        private const val ZERO_CAREER = 0
    }
}