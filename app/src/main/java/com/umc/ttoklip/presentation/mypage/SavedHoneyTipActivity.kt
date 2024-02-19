package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySavedHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadActivity
import com.umc.ttoklip.presentation.mypage.adapter.HoneyTip
import com.umc.ttoklip.presentation.mypage.adapter.OnSpinnerItemClickListener
import com.umc.ttoklip.presentation.mypage.adapter.SavedHoneyTipAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedHoneyTipActivity :
    BaseActivity<ActivitySavedHoneyTipBinding>(R.layout.activity_saved_honey_tip),
    OnSpinnerItemClickListener {
    override fun initView() {
        val sortFilters = listOf(
            getString(R.string.sort_most_recent),
            getString(R.string.sort_popularity),
            getString(R.string.sort_most_comments),
            getString(R.string.sort_most_scrap)
        )
        val typeFilters = listOf(
            "꿀팁 공유", "뉴스레터", "소통해요"
        )
        binding.honeyTipFilterSpinner.adapter =
            SortSpinnerAdapter(this@SavedHoneyTipActivity, sortFilters)
        binding.honeyTipFilterSpinner.setSelection(0)

        binding.honeyTipTypeSpinner.adapter =
            SortSpinnerAdapter(this@SavedHoneyTipActivity, typeFilters)
        binding.honeyTipTypeSpinner.setSelection(0)

        val honeyTipList = listOf(
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTip("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
        )
        val adapter = SavedHoneyTipAdapter(this)
        binding.savedHoneyTipRv.layoutManager = LinearLayoutManager(this@SavedHoneyTipActivity)
        binding.savedHoneyTipRv.adapter = adapter
        adapter.submitList(honeyTipList)

        binding.savedHoneyTipBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() = Unit

    override fun onClick(honeyTip: HoneyTip) {
        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
    }

}