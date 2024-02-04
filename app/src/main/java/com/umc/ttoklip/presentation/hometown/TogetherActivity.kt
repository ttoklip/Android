package com.umc.ttoklip.presentation.hometown

import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTogetherBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.adapter.Transaction
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TogetherActivity : BaseActivity<ActivityTogetherBinding>(R.layout.activity_together) {
    private val adapter by lazy {
        TransactionAdapter(this)
    }

    override fun initView() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        val dummy = listOf(
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            )
        )
        binding.togetherRv.adapter = adapter
        binding.togetherRv.layoutManager = LinearLayoutManager(this)
        adapter.submitList(dummy)
    }

    override fun initObserver() {

    }

}