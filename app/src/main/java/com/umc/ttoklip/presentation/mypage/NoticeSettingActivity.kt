package com.umc.ttoklip.presentation.mypage

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityNoticeSettingBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class NoticeSettingActivity :
    BaseActivity<ActivityNoticeSettingBinding>(R.layout.activity_notice_setting) {
    override fun initView() {
        binding.noticeSettingBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() = Unit
}