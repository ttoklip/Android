package com.umc.ttoklip.presentation.hometown

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.Communication
import com.umc.ttoklip.presentation.hometown.adapter.CommunicationAdapter
import com.umc.ttoklip.presentation.hometown.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.mypage.SortSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunicationActivity :
    BaseActivity<ActivityCommunicationBinding>(R.layout.activity_communication),
    OnItemClickListener {
    override fun initView() {
        binding.writeFab.setOnClickListener {
            val intent = Intent(this, WriteCommunicationActivity::class.java)
            startActivity(intent)
        }
        val sortFilters = listOf(
            getString(R.string.sort_most_recent),
            getString(R.string.sort_popularity),
            getString(R.string.sort_most_comments),
            getString(R.string.sort_most_scrap)
        )
        binding.honeyTipFilterSpinner.adapter =
            SortSpinnerAdapter(this, sortFilters)
        binding.honeyTipFilterSpinner.setSelection(0)

        val honeyTipList = listOf(
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
            Communication(
                "똑똑이",
                "음식물 쓰레기 냄새 방지!!",
                "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                "1일전",
                0
            ),
        )
        val adapter = CommunicationAdapter(this)
        binding.communicationRv.layoutManager = LinearLayoutManager(this)
        binding.communicationRv.adapter = adapter
        adapter.submitList(honeyTipList)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {

    }

    override fun onClick(communication: Communication) {

    }

}