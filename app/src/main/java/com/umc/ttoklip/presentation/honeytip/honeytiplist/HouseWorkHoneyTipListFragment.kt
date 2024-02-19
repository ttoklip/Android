package com.umc.ttoklip.presentation.honeytip.honeytiplist

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
class HouseWorkHoneyTipListFragment :
    BaseFragment<FragmentHoneyTipListBinding>(R.layout.fragment_honey_tip_list),
    OnItemClickListener {
    private val honeyTipListRVA by lazy {
        HoneyTipListRVA(this)
    }
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = { requireParentFragment().requireParentFragment() }
    )

    private var totalPage = 0
    private var page = 0
    private var isLast = false

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.houseworkHoneyTip.collect {
                    honeyTipListRVA.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.honeyTipPaging.collect {
                    Log.d("it", it.toString())
                    totalPage = it.totalPage
                    isLast = it.isLast
                    honeyTipListRVA.submitList(honeyTipListRVA.currentList.toMutableList().apply { addAll(it.data)})
                }
            }
        }
    }

    override fun initView() {
        //viewModel.getHoneyTipByCategory("HOUSEWORK", 0)
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
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalItemViewCount = recyclerView.adapter!!.itemCount - 1

                if (newState == 2 && !recyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == totalItemViewCount
                ) {
                    Log.d("end", "end")
                   //viewModel.getHoneyTipByCategory("HOUSE", page++)
                }
            }

        })
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
}