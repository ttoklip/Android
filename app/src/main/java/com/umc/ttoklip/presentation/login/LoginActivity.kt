package com.umc.ttoklip.presentation.login

import android.content.Intent
import android.content.SharedPreferences
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityLoginBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.signup.SignupActivity

class LoginActivity:BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private var isSaveId:Boolean=false

    override fun initView() {
        val spf=getSharedPreferences("id", MODE_PRIVATE)
        loadSaveId(spf)
        binding.loginLoginBtn.setOnClickListener {
            setSaveId(isSaveId,spf)
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.loginSignupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.loginSaveIdIv.setOnClickListener {
            setSaveIdStatus(!isSaveId)
        }
    }

    private fun loadSaveId(spf: SharedPreferences) {
        isSaveId=spf.getBoolean("saveIdCheck",false)
        setSaveIdStatus(isSaveId)
        binding.loginIdEt.setText(spf.getString("idString",""))
    }
    private fun setSaveId(isSaveId: Boolean, spf: SharedPreferences){
        val editor=spf.edit()
        if (isSaveId){//나중에 로그인 ok도 조건으로 달기
            editor.putBoolean("saveIdCheck",true)
            editor.putString("idString",binding.loginIdEt.text.toString().trim())
            editor.apply()
        }else{
            editor.putBoolean("saveIdCheck",false)
            editor.remove("idString")
            editor.apply()
        }
    }

    private fun setSaveIdStatus(isSaveId: Boolean) {
        if(isSaveId){
            this.isSaveId=true
            binding.loginSaveIdIv.setImageResource(R.drawable.ic_check_on_20)
        }else{
            this.isSaveId=false
            binding.loginSaveIdIv.setImageResource(R.drawable.ic_check_off_20)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun initObserver() {
    }

}