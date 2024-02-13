package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import androidx.activity.OnBackPressedCallback
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup5Binding
import com.umc.ttoklip.databinding.FragmentSignupTermBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class SignupTermFragment : BaseFragment<FragmentSignupTermBinding>(R.layout.fragment_signup_term) {
    private lateinit var callback:OnBackPressedCallback

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback=object:OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val activity=activity as SignupActivity
                activity.termBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }
}