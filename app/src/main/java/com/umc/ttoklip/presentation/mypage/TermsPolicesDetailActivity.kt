package com.umc.ttoklip.presentation.mypage

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTermBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class TermsPolicesDetailActivity: BaseActivity<ActivityTermBinding>(R.layout.activity_term) {
    override fun initView() {
        binding.termCancelIb.setOnClickListener {
            finish()
        }

        binding.termTitleTv.text=intent.getStringExtra("title")
        binding.termDetailTv.text=intent.getStringExtra("content")
    }

    override fun initObserver() = Unit

}