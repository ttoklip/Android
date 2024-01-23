package com.umc.ttoklip.presentation.alarm

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityAlarmBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class AlarmActivity : BaseActivity<ActivityAlarmBinding>(R.layout.activity_alarm) {

    private val viewModel: AlarmViewModel by viewModels<AlarmViewModelImpl>()


    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    override fun initObserver() {

    }

    companion object{
        const val ALARM = "alarm"

        fun newIntent(context : Context) =
            Intent(context, AlarmActivity::class.java)

    }

}