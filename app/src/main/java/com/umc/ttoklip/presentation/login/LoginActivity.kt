package com.umc.ttoklip.presentation.login

import android.content.Intent
import android.content.SharedPreferences
import android.text.InputType
import android.view.View
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityLoginBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.signup.SignupActivity

class LoginActivity:BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private var isSaveId:Boolean=false
    private var isShowPw:Boolean=false

    override fun initView() {
        binding.loginNaverBtn.setOnClickListener {
            //임시로 연결
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.loginKakaoBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun initObserver() {
    }

}