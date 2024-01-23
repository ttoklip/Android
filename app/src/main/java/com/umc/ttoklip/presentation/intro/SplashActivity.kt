package com.umc.ttoklip.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class SplashActivity<V:ViewDataBinding>(@LayoutRes val layoutResource:Int):
    AppCompatActivity() {
    private var _binding:V?=null
    protected val binding:V get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=DataBindingUtil.setContentView(this,layoutResource)

        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
                            //이어질 activity 이름 확인하기
            //startActivity(Intent(this,MainActivity::class.java))
        },2000)

    }
}