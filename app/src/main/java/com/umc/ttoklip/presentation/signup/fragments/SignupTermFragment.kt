package com.umc.ttoklip.presentation.signup.fragments

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup5Binding
import com.umc.ttoklip.databinding.FragmentSignupTermBinding
import com.umc.ttoklip.presentation.base.BaseFragment

class SignupTermFragment : BaseFragment<FragmentSignupTermBinding>(R.layout.fragment_signup_term) {
    override fun initObserver() {
    }

    override fun initView() {
        var termType = requireArguments().getString("termType")
        if (termType.toString().equals("service")) {
            binding.signupTermTitleTv.text = getString(R.string.service_term)
            binding.signupTermDetailTv.text = getString(R.string.service_term_detail)
        } else if (termType.toString().equals("privacy")) {
            binding.signupTermTitleTv.text = getString(R.string.privacy_term)
            binding.signupTermDetailTv.text = getString(R.string.privacy_term_detail)
        } else {
            binding.signupTermTitleTv.text = getString(R.string.location_term)
            binding.signupTermDetailTv.text = getString(R.string.location_term_detail)
        }
    }
}