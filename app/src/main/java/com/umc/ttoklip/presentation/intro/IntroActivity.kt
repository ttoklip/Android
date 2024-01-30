package com.umc.ttoklip.presentation.intro

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityIntroBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.login.LoginActivity

class IntroActivity:BaseActivity<ActivityIntroBinding>(R.layout.activity_intro){
    override fun initView() {
        binding.introIntroVp.apply {
            adapter=IntroVPAdapter(this@IntroActivity,3)
            registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position){
                        2->binding.introNextBtn.text="똑립 시작하기"
                        else->binding.introNextBtn.text="다음"
                    }
                }
            })
        }
        binding.introIndicator.attachTo(binding.introIntroVp)

        binding.introNextBtn.setOnClickListener {
            if(binding.introIntroVp.currentItem==2) {
                val spf=getSharedPreferences("first", MODE_PRIVATE)
                val editor=spf.edit()
                editor.putBoolean("firstRun",false)
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                binding.introIntroVp.currentItem++
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun initObserver() {
    }
}