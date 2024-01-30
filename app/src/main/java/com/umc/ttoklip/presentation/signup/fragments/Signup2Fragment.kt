package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup1Binding
import com.umc.ttoklip.databinding.FragmentSignup2Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup2Fragment: BaseFragment<FragmentSignup2Binding>(R.layout.fragment_signup2) {

    private lateinit var callback:OnBackPressedCallback

    override fun initObserver() {
    }

    override fun initView() {
        val activity=activity as SignupActivity
        activity?.setTitle("2단계 - 계정 설정",2)
        binding.signup2NextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signup2_fragment_to_signup3_fragment)
        }
    }
}