package com.umc.ttoklip.presentation.signup.fragments

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup4Binding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup4Fragment: BaseFragment<FragmentSignup4Binding>(R.layout.fragment_signup4) {
    override fun initObserver() {
    }

    override fun initView() {
        val activity=activity as SignupActivity
        activity?.setTitle("4단계 - 프로필 설정",4)
        binding.signup4NextBtn.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}