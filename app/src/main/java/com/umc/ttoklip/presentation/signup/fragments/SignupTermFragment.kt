package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup5Binding
import com.umc.ttoklip.databinding.FragmentSignupTermBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupTermFragment : BaseFragment<FragmentSignupTermBinding>(R.layout.fragment_signup_term) {
    private lateinit var callback: OnBackPressedCallback

    private val viewModel: TermViewModel by activityViewModels()

    override fun initObserver() {
    }

    override fun initView() {
        val termId=arguments?.getInt("termId")?:0
        val title = viewModel.termDatas.value[termId].title
//            arguments?.getString("title")
        val content = viewModel.termDatas.value[termId].content
//            arguments?.getString("content")
        binding.signupTermTitleTv.text = title
        binding.signupTermDetailTv.text = content
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val activity = activity as SignupActivity
                activity.termBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}