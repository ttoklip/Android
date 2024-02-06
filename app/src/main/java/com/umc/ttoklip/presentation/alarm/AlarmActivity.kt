package com.umc.ttoklip.presentation.alarm

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityAlarmBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.news.adapter.Dummy
import com.umc.ttoklip.presentation.search.adapter.AlarmRVA
import com.umc.ttoklip.presentation.search.adapter.SearchRVA

class AlarmActivity : BaseActivity<ActivityAlarmBinding>(R.layout.activity_alarm) {

    private val viewModel: AlarmViewModel by viewModels<AlarmViewModelImpl>()
    private val alarmRVA by lazy {
        AlarmRVA({})
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.alarmRV.adapter = alarmRVA
        alarmRVA.submitList(
            listOf(
                Dummy("1"),
                Dummy("2")
            )
        )
    }

    override fun initObserver() {

    }

    companion object{
        const val ALARM = "alarm"

        fun newIntent(context : Context) =
            Intent(context, AlarmActivity::class.java)

    }

}