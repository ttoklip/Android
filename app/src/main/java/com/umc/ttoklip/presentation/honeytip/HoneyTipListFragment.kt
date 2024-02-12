package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentHoneyTipListBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.read.ReadActivity


class HoneyTipListFragment :
    BaseFragment<FragmentHoneyTipListBinding>(R.layout.fragment_honey_tip_list),
    OnItemClickListener {
    private val viewModel: HoneyTipViewModel by activityViewModels()
    private var board = ""
    override fun initObserver() {
    }

    override fun initView() {
        initRV()
        viewModel.boardLiveData.observe(this){
            board = it
            Log.d("HoneyTipListFragment change board", it)
        }
    }

    private fun initRV() {
        val honeyTipList = listOf(
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
        )

        binding.rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val honeyTipListRVA = HoneyTipListRVA(this)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rv.adapter = honeyTipListRVA
        honeyTipListRVA.submitList(honeyTipList)

    }

    override fun onClick(honeyTips: HoneyTips) {
        val intent = Intent(activity, ReadActivity::class.java)
        intent.putExtra(BOARD, board)
        Log.d("HoneyTipListFragment", board)
        startActivity(intent)
    }
}