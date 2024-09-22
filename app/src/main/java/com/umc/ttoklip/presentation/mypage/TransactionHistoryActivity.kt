package com.umc.ttoklip.presentation.mypage

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.databinding.ActivityTransactionHistoryBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.together.read.ReadTogetherActivity
import com.umc.ttoklip.presentation.mypage.adapter.OnTogetherItemClickListener
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import com.umc.ttoklip.presentation.mypage.vm.HistoryViewModel
import com.umc.ttoklip.presentation.otheruser.OtherTipActivity
import com.umc.ttoklip.presentation.otheruser.OtherTipActivity.Companion.OTHERID
import com.umc.ttoklip.presentation.otheruser.OtherTipActivity.Companion.OTHERNAME
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionHistoryActivity :
    BaseActivity<ActivityTransactionHistoryBinding>(R.layout.activity_transaction_history),
    OnTogetherItemClickListener {

    private val viewModel: HistoryViewModel by viewModels<HistoryViewModel>()

    private val adapter by lazy {
        TransactionAdapter(this, this)
    }

    override fun initView() {

        binding.transactionHistoryRv.adapter = adapter
        binding.transactionHistoryRv.layoutManager = LinearLayoutManager(this)

        if (intent.getIntExtra(MYOROTEHR, 1) == 1) {
            viewModel.getMyHistories()
        } else {

        }

        binding.myTransactionHistoryBackBtn.setOnSingleClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.transactionHistoryRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalItemViewCount = recyclerView.adapter!!.itemCount - 1

                if (newState == 2 && !recyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == totalItemViewCount
                ) {
                    if (intent.getIntExtra(MYOROTEHR, 1) == 1) {
                        viewModel.getMyHistories()
                    } else {

                    }
                }
            }

        })

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.histories.collect{
                    adapter.submitList(it)
                }
            }
        }
    }
    override fun onClick(together: Togethers) {
        val intent = Intent(this, ReadTogetherActivity::class.java)
        intent.putExtra("postId", together.id)
        startActivity(intent)
    }

    companion object {
        const val MYOROTEHR = "my_or_other" // 1 :me  2: other
        const val NAME = "other_name"
        fun newIntent(context: Context, id: Int, user: String) =
            Intent(context, TransactionHistoryActivity::class.java).apply {
                putExtra(MYOROTEHR, id)
                putExtra(NAME, user)
            }
    }
}