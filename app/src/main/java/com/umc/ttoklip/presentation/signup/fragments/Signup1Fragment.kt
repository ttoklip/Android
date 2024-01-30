package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup1Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup1Fragment: BaseFragment<FragmentSignup1Binding>(R.layout.fragment_signup1) {

    override fun initObserver() {
    }

    override fun initView() {
        val activity=activity as SignupActivity
        activity?.setTitle("1단계 - 개인정보 입력",1)
        binding.signup1NextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signup1_fragment_to_signup2_fragment)
        }
    }
}