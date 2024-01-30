package com.umc.ttoklip.presentation.signup

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySignupBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class SignupActivity:BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    override fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.signup_frm)as NavHostFragment
        var navController=navHostFragment.findNavController()
        binding.loginBackIb.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun initObserver() {
    }

    fun setTitle(title:String,step:Int){
        binding.loginTitleTv.text=title
        binding.signupProgressbar.progress=step
    }
}
