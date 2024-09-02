package com.umc.ttoklip.presentation.otheruser

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ActivityOtherHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtherTipActivity :
    BaseActivity<ActivityOtherHoneyTipBinding>(R.layout.activity_other_honey_tip),
    OnItemClickListener {

    private val viewModel: OtherTipViewModel by viewModels<OtherTipViewModel>()

    private val adapter by lazy {
        HoneyTipListRVA(this,this)
    }

    override fun initView() {
        binding.userT.text = intent.getStringExtra(OTHERNAME) ?: "USER"
        binding.myHoneyTipRv.adapter = adapter
        viewModel.getTips(intent.getIntExtra(OTHERID, 0))
        binding.myHoneyTipBackBtn.setOnClickListener {
            finish()
        }

        binding.myHoneyTipRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalItemViewCount = recyclerView.adapter!!.itemCount - 1

                if (newState == 2 && !recyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == totalItemViewCount
                ) {
                    viewModel.getTips(intent.getIntExtra(OTHERID, 0))
                }
            }

        })
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tips.collect { tips ->
                    adapter.submitList(tips)
                }
            }
        }
    }

    override fun onClick(honeyTip: HoneyTipMain) {
        val intent = Intent(this, ReadHoneyTipActivity::class.java)
        intent.putExtra("postId", honeyTip.id)
        startActivity(intent)
    }

    companion object {
        const val OTHERID = "other_id"
        const val OTHERNAME = "other_name"
        fun newIntent(context: Context, id: Int, user: String) =
            Intent(context, OtherTipActivity::class.java).apply {
                putExtra(OTHERID, id)
                putExtra(OTHERNAME, user)
            }
    }
}