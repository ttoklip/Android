package com.umc.ttoklip.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySplashBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class IntroActivity:BaseActivity<ActivitySplashBinding>(R.layout.activity_splash){
    override fun initView() {
        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            //activity 이름 알아오기
            //startActivity(Intent(this,MainActivity::class.java))
        },2000)
    }
    override fun initObserver() {
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}