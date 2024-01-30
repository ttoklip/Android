package com.umc.ttoklip.presentation.honeytip.read

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil.setContentView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityReadBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.DetailHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.dialog.ImageDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipFragment

class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read) {
    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailHoneyTipFragment())
            .commit()
        setTitle()

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.dotBtn.setOnClickListener {
            binding.reportBtn.isVisible = true
        }

        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener{
                override fun onClick() {
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

    override fun initObserver() {

    }

    private fun setTitle(){
        Log.d("ReadActivity", intent.getStringExtra(BOARD)!!)
        binding.titleT.text = when(intent.getStringExtra(BOARD)){
            HONEY_TIP -> "꿀팁 공유하기"
            else -> "질문하기"
        }
    }
}