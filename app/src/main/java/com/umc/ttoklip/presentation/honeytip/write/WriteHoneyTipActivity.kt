package com.umc.ttoklip.presentation.honeytip.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.DetailHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipViewModel

class WriteHoneyTipActivity : BaseActivity<ActivityHoneyTipBinding>(R.layout.activity_honey_tip) {
    private val honeyTipViewModel: HoneyTipViewModel by viewModels()
    private var body = ""
    private var title = ""
    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, WriteHoneyTipFragment(intent.getStringExtra(BOARD) ?: ""))
            .commit()
        binding.backBtn.setOnClickListener {
            finish()
        }
        /*honeyTipViewModel.boardLiveData.observe(this){
            Log.d("WriteActivity", it)
            binding.titleTv.text = it
        }*/
        setTitle()
        binding.writeDoneBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {
        honeyTipViewModel.bodyLiveData.observe(this){
            body = it

            binding.writeDoneBtn.isEnabled = if(body.isNotBlank() && title.isNotBlank()) true
            else false
        }

        honeyTipViewModel.titleLiveData.observe(this){
            title = it
            binding.writeDoneBtn.isEnabled = if(body.isNotBlank() && title.isNotBlank()) true
            else false
        }
    }

    private fun setTitle(){
        binding.titleTv.text = when(intent.getStringExtra(BOARD)){
            HONEY_TIP -> "꿀팁 공유하기"
            else -> "질문하기"
        }
    }
}