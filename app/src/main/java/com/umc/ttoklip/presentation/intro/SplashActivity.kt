package com.umc.ttoklip.presentation.intro

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.ActivitySplashBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.login.LoginActivity

class SplashActivity:BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun initView() {
        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            val spf=getSharedPreferences("first", MODE_PRIVATE)
            val firstRun=spf.getBoolean("firstRun",true)
            val jwt=TtoklipApplication.prefs.getString("jwt","")
            val isFirstLogin=TtoklipApplication.prefs.getBoolean("isFirstLogin",true)
            if(firstRun){
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }else if(jwt.isNotEmpty()&&!isFirstLogin){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        },2000)
    }
    override fun initObserver() {
    }
}