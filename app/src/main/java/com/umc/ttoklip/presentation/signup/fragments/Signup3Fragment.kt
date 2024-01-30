package com.umc.ttoklip.presentation.signup.fragments

import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup3Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup3Fragment: BaseFragment<FragmentSignup3Binding>(R.layout.fragment_signup3) {
    override fun initObserver() {
    }

    override fun initView() {
        val activity=activity as SignupActivity
        activity?.setTitle("3단계 - 약관 동의",3)
        binding.signup3NextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signup3_fragment_to_signup4_fragment)
        }
    }
}