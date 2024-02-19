package com.umc.ttoklip.presentation.mypage

import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.databinding.ActivityTransactionHistoryBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.adapter.OnTogetherItemClickListener
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryActivity :
    BaseActivity<ActivityTransactionHistoryBinding>(R.layout.activity_transaction_history),
    OnTogetherItemClickListener {
    private val adapter by lazy {
        TransactionAdapter(this, this)
    }

    override fun initView() {

        binding.transactionHistoryRv.adapter = adapter
        binding.transactionHistoryRv.layoutManager = LinearLayoutManager(this)

        binding.myTransactionHistoryBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() = Unit
    override fun onClick(together: Togethers) {

    }
}