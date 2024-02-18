package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup3Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup3Fragment : BaseFragment<FragmentSignup3Binding>(R.layout.fragment_signup3) {

    var allAgree: Boolean = false
    var serviceAgree: Boolean = false
    var privacyAgree: Boolean = false
    var locationAgree: Boolean = false

    override fun initObserver() {
    }

    override fun initView() {
        val activity = activity as SignupActivity
        activity?.setProg(1)

        binding.signup3AgreeAllIv.setOnClickListener {
            if (allAgree) {
                binding.signup3AgreeServiceIv.setImageResource(R.drawable.oval_stroke_1)
                binding.signup3AgreePrivacyIv.setImageResource(R.drawable.oval_stroke_1)
                binding.signup3AgreeLocationIv.setImageResource(R.drawable.oval_stroke_1)
                serviceAgree = false
                privacyAgree = false
                locationAgree = false
            } else {
                binding.signup3AgreeServiceIv.setImageResource(R.drawable.oval_double)
                binding.signup3AgreePrivacyIv.setImageResource(R.drawable.oval_double)
                binding.signup3AgreeLocationIv.setImageResource(R.drawable.oval_double)
                serviceAgree = true
                privacyAgree = true
                locationAgree = true
            }
            allAgreeCheck()
            nextok()
        }

        binding.signup3AgreeServiceIv.setOnClickListener {
            if(serviceAgree){
                serviceAgree=false
                binding.signup3AgreeServiceIv.setImageResource(R.drawable.oval_stroke_1)
            }else{
                serviceAgree=true
                binding.signup3AgreeServiceIv.setImageResource(R.drawable.oval_double)
            }
            allAgreeCheck()
            nextok()
        }
        binding.signup3AgreeServiceBtn.setOnClickListener {
            activity.updateButtonForTerm()
            findNavController().navigate(R.id.action_signup3_fragment_to_signupTerm_fragment,
                bundleOf("termType" to "service"))
        }

        binding.signup3AgreePrivacyIv.setOnClickListener {
            if(privacyAgree){
                privacyAgree=false
                binding.signup3AgreePrivacyIv.setImageResource(R.drawable.oval_stroke_1)
            }else{
                privacyAgree=true
                binding.signup3AgreePrivacyIv.setImageResource(R.drawable.oval_double)
            }
            allAgreeCheck()
            nextok()
        }
        binding.signup3AgreePrivacyBtn.setOnClickListener {
            activity.updateButtonForTerm()
            findNavController().navigate(R.id.action_signup3_fragment_to_signupTerm_fragment,
                bundleOf("termType" to "privacy"))
        }

        binding.signup3AgreeLocationIv.setOnClickListener {
            if(locationAgree){
                locationAgree=false
                binding.signup3AgreeLocationIv.setImageResource(R.drawable.oval_stroke_1)
            }else{
                locationAgree=true
                binding.signup3AgreeLocationIv.setImageResource(R.drawable.oval_double)
            }
            allAgreeCheck()
        }

        binding.signup3AgreeLocationBtn.setOnClickListener {
            activity.updateButtonForTerm()
            findNavController().navigate(R.id.action_signup3_fragment_to_signupTerm_fragment,
                bundleOf("termType" to "location"))
        }

        binding.signup3NextBtn.setOnClickListener {
            if (serviceAgree && privacyAgree) {
                findNavController().navigate(R.id.action_signup3_fragment_to_signup4_fragment)
            }
        }
    }
    private fun allAgreeCheck() {
        if (serviceAgree && privacyAgree && locationAgree) {
            allAgree = true
            binding.signup3AgreeAllIv.setImageResource(R.drawable.oval_double)
        } else {
            allAgree = false
            binding.signup3AgreeAllIv.setImageResource(R.drawable.oval_stroke_1)
        }
    }
    private fun nextok(){
        if(serviceAgree&&privacyAgree){
            binding.signup3NextBtn.isClickable=true
            binding.signup3NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
            binding.signup3NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
        }else{
            binding.signup3NextBtn.isClickable=false
            binding.signup3NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
            binding.signup3NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
        }
    }
}