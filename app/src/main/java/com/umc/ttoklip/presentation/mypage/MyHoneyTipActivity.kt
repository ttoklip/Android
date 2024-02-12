package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
import com.umc.ttoklip.databinding.ActivityMyHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.read.ReadActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyHoneyTipActivity : BaseActivity<ActivityMyHoneyTipBinding>(R.layout.activity_my_honey_tip),
    OnItemClickListener {
    override fun initView() {
        val sortFilters = listOf(
            getString(R.string.sort_most_recent),
            getString(R.string.sort_popularity),
            getString(R.string.sort_most_comments),
            getString(R.string.sort_most_scrap)
        )
        binding.honeyTipFilterSpinner.adapter =
            SortSpinnerAdapter(this, sortFilters)
        binding.honeyTipFilterSpinner.setSelection(0)

        /*val honeyTipList = listOf(
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
        )*/
        val honeyTipList = mutableListOf<HoneyTipResponse>()
        val adapter = HoneyTipListRVA(this)
        binding.myHoneyTipRv.layoutManager = LinearLayoutManager(this)
        binding.myHoneyTipRv.adapter = adapter
        binding.myHoneyTipRv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.submitList(honeyTipList)

        binding.myHoneyTipBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() = Unit


    /*override fun onClick(honeyTips: HoneyTips) {
        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
    }*/

    override fun onClick(honeyTipResponse: HoneyTipResponse) {

    }
}