package com.umc.ttoklip.presentation.alarm

import android.content.Context
import android.content.Intent
import com.umc.ttoklip.R
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.databinding.ActivityAlarmBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.together.read.ReadTogetherActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadQuestionActivity
import com.umc.ttoklip.presentation.search.adapter.AlarmRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlarmActivity : BaseActivity<ActivityAlarmBinding>(R.layout.activity_alarm) {

    private val viewModel: AlarmViewModel by viewModels<AlarmViewModel>()
    private val alarmRVA by lazy {
        AlarmRVA {
            when (it.title) {
                "꿀팁 공유해요" -> {
                    startActivity(ReadHoneyTipActivity.newIntent(this, it.targetClassId))
                }

                "우리동네 덧글", "우리동네 답글" -> {
                    startActivity(ReadTogetherActivity.newIntent(this, it.targetClassId.toLong()))
                }

                "질문해요" -> {
                    startActivity(ReadQuestionActivity.newIntent(this, it.targetClassId))
                }

                else -> Unit
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.alarmRV.adapter = alarmRVA
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notiList.collect {
                    alarmRVA.submitList(it)
                    Log.d("확인", "${it.map { it.notificationId }}")
                }
            }
        }
    }

    companion object {
        const val ALARM = "alarm"

        fun newIntent(context: Context) =
            Intent(context, AlarmActivity::class.java)

    }

}