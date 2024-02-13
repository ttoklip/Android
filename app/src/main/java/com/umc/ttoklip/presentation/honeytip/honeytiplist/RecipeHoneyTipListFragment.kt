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
import com.umc.ttoklip.data.model.honeytip.HoneyTipResponse
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
class RecipeHoneyTipListFragment: BaseFragment<FragmentHoneyTipListBinding>(R.layout.fragment_honey_tip_list),
    OnItemClickListener {
    private val honeyTipListRVA by lazy {
        HoneyTipListRVA(this)
    }
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = {requireParentFragment().requireParentFragment()}
    )
    private var board = HONEY_TIP
    override fun initObserver() {
        viewModel.boardLiveData.observe(this){
            board = it
            Log.d("board", board.toString())
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.honeyTip.collect {
                    val intent = Intent(activity, ReadHoneyTipActivity::class.java)
                    intent.putExtra(BOARD, board)
                    intent.putExtra("honeyTip", it)
                    Log.d("HoneyTipListFragment", board)
                    startActivity(intent)
                }
            }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                if (board == HONEY_TIP) {
                    viewModel.recipeHoneyTip.collect {
                        honeyTipListRVA.submitList(it)
                    }
                } else {
                    viewModel.recipeQuestion.collect{
                        honeyTipListRVA.submitList(it)
                    }
                }
            }
        }
    }
    override fun initView() {
        initRV()
    }

    private fun initRV() {
        binding.rv.addItemDecoration(
            DividerItemDecoration(requireContext(),
            LinearLayoutManager.VERTICAL)
        )
        binding.rv.adapter = honeyTipListRVA
    }

    override fun onClick(honeyTipResponse: HoneyTipResponse) {
        val intent = Intent(activity, ReadHoneyTipActivity::class.java)
        intent.putExtra(BOARD, board)
        intent.putExtra("honeyTipId", honeyTipResponse.honeyTipId)
        startActivity(intent)
    }
}