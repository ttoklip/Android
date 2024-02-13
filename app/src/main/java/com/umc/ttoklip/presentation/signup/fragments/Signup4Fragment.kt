package com.umc.ttoklip.presentation.signup.fragments

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup4Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.mypage.ChooseMainInterestDialogFragment
import com.umc.ttoklip.presentation.mypage.InputIndependentCareerDialogFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import com.umc.ttoklip.presentation.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Signup4Fragment : BaseFragment<FragmentSignup4Binding>(R.layout.fragment_signup4) {

    private val viewModel: SignupViewModel by viewModels()
    private lateinit var interestArray:ArrayList<String>

    private var independentCareerYear:Int? =null
    private var independentCareerMonth: Int? =null

    private var nickok:Boolean=false
    private var independentCareerNotNull:Boolean=false
    private var interestNotNull:Boolean=false

    override fun initObserver() {
    }

    override fun initView() {
        interestArray=ArrayList()
        val activity = activity as SignupActivity
        activity?.setProg(2)

        binding.signup4NickcheckButton.setOnClickListener {
            //닉네임 확인 로직
            viewModel.nickCheck(binding.signup4NicknameEt.text.toString())
            if(viewModel.nickok.value){
                binding.signup4NickokTv.visibility = View.VISIBLE
                binding.signup4NicknotokTv.visibility = View.GONE
                nickok=true
            }else{
                binding.signup4NickokTv.visibility = View.GONE
                binding.signup4NicknotokTv.visibility = View.VISIBLE
                nickok=false
            }
            nextok()
        }

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

        binding.signup4IndependenceEt.setOnClickListener {
            independentCareerNotNull=true
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

            nextok()
        }

        binding.signup4InterestGroup.setOnClickListener {
            binding.signup4InterestDescTv.visibility = View.GONE
            binding.signup4InterestIconIv.visibility = View.GONE
            //chip 클릭해야 발생
            val bottomSheet = ChooseMainInterestDialogFragment { interests ->
                binding.signup4InterestGroup.removeAllViews()
                interestArray.clear()
                interests.forEach { interest ->
                    val chip = Chip(activity)
                    chip.text = interest
                    chip.setTextAppearance(R.style.TextAppearance_App_12sp_700)
                    chip.setTextColor(ContextCompat.getColor(activity, R.color.black))
                    chip.setChipBackgroundColorResource(R.color.yellow)
                    chip.setChipStrokeColorResource(R.color.yellow)
                    binding.signup4InterestGroup.addView(chip)

                    interestArray.add(interest)
                }
                interestNotNull=true
            }
            bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)

            nextok()
        }

        binding.signup4NextBtn.setOnClickListener {
            if(nickok&&independentCareerNotNull&&interestNotNull){
                viewModel.saveUserInfo(
                    binding.signup4NicknameEt.text.toString(),
                    interestArray,
                    binding.signup4ProfileImageIv.resources.toString(),
                    independentCareerYear!!,
                    independentCareerMonth!!
                )
                findNavController().navigate(R.id.action_signup4_fragment_to_signup5_fragment)
            }
        }
    }

    private fun nextok(){
        if(nickok&&independentCareerNotNull&&interestNotNull){
            binding.signup4NextBtn.isClickable=true
            binding.signup4NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
            binding.signup4NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
        }else{
            binding.signup4NextBtn.isClickable=false
            binding.signup4NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
            binding.signup4NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
        }
    }

    companion object {
        private const val ZERO_CAREER = 0
    }
}