package com.umc.ttoklip.presentation.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTransactionHistoryBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.adapter.Transaction
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryActivity :
    BaseActivity<ActivityTransactionHistoryBinding>(R.layout.activity_transaction_history) {
    private val adapter by lazy {
        TransactionAdapter(this)
    }

    override fun initView() {
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
            )
        )
        binding.transactionHistoryRv.adapter = adapter
        binding.transactionHistoryRv.layoutManager = LinearLayoutManager(this)
        adapter.submitList(dummy)

        binding.myTransactionHistoryBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() = Unit
}