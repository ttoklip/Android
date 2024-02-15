package com.umc.ttoklip.presentation.honeytip

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.FragmentHoneyTipListBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HoneyTipListFragment() :
    BaseFragment<FragmentHoneyTipListBinding>(R.layout.fragment_honey_tip_list),
    OnItemClickListener {
    private val honeyTipListRVA by lazy {
        HoneyTipListRVA(this)
    }
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = {requireParentFragment().requireParentFragment()}
    )
    private var board = ""
    override fun initObserver() {
        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.honeyTip.collect {
                    val intent = Intent(activity, ReadHoneyTipActivity::class.java)
                    intent.putExtra(BOARD, board)
                    intent.putExtra("honeyTip", it)
                    Log.d("HoneyTipListFragment", board)
                    startActivity(intent)
                }
            }
        }*/

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.houseworkHoneyTip.collect{
                        Log.d("in list", it.toString())
                        honeyTipListRVA.submitList(it)
                    }
                }
        }


        viewModel.boardLiveData.observe(viewLifecycleOwner){
            board = it
        }
    }

    override fun initView() {
        Log.d("initview", "시작")
        initRV()
        //Log.d("parent", parentFragment.toString())
        /*viewModel.boardLiveData.observe(this) {
            board = it
            Log.d("HoneyTipListFragment change board", it)
        }*/
    }

    private fun initRV() {
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
        //val honeyTipList = mutableListOf<HoneyTipResponse>()

        /*binding.rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)*/
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rv.adapter = honeyTipListRVA
        //honeyTipListRVA.submitList(honeyTipList)
    }

    override fun onClick(honeyTip: HoneyTipMain) {
        //viewModel.inquireHoneyTip(honeyTipResponse.honeyTipId)
        Log.d("honeyTipId", honeyTip.id.toString())
        val intent = Intent(activity, ReadHoneyTipActivity::class.java)
        intent.putExtra(BOARD, board)
        intent.putExtra("honeyTipId", honeyTip.id)
        Log.d("HoneyTipListFragment", honeyTip.toString())
        startActivity(intent)
    }
}