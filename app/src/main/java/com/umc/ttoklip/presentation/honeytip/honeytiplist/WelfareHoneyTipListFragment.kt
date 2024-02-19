package com.umc.ttoklip.presentation.honeytip.honeytiplist

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
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HONEY_TIP
import com.umc.ttoklip.presentation.honeytip.HoneyTipViewModel
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelfareHoneyTipListFragment :
    BaseFragment<FragmentHoneyTipListBinding>(R.layout.fragment_honey_tip_list),
    OnItemClickListener {
    private val honeyTipListRVA by lazy {
        HoneyTipListRVA(this)
    }
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = { requireParentFragment().requireParentFragment() }
    )

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.welfareHoneyTip.collect {
                    honeyTipListRVA.submitList(it)
                }
            }
        }
    }

    override fun initView() {
        initRV()
    }

    private fun initRV() {
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rv.adapter = honeyTipListRVA
    }

    override fun onClick(honeyTip: HoneyTipMain) {
        val intent = Intent(activity, ReadHoneyTipActivity::class.java)
        intent.putExtra("postId", honeyTip.id)
        Log.d("Clicked honeyTip", honeyTip.toString())
        Log.d("postId", honeyTip.id.toString())
        startActivity(intent)
    }
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onPause() {
        super.onPause()
        Log.d("pause", "pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("stop", "stop")
        viewModel.resetHoneyTipList("WELFARE_POLICY")
    }
}