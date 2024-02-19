package com.umc.ttoklip.presentation.honeytip.honeytiplist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
class HouseWorkHoneyTipListFragment : Fragment(),
    OnItemClickListener {
    private val honeyTipListRVA by lazy {
        HoneyTipListRVA(this)
    }
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = { requireParentFragment().requireParentFragment() }
    )

    lateinit var binding: FragmentHoneyTipListBinding

    private var totalPage = 0
    private var page = 0
    private var isLast = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_honey_tip_list,
            null,
            false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    fun initObserver() {
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
                }
            }
        }
    }

    fun initView() {
        //viewModel.getHoneyTipByCategory("HOUSEWORK", 0)
        initRV()
        binding.sv.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            val view: View? = binding.sv.getChildAt(binding.sv.childCount - 1)
            if (view != null) {
                val diff: Int = view.bottom - (binding.sv.height + binding.sv
                    .scrollY)
                if (diff == 0) {
                    viewModel.getHoneyTipByCategory()
                }
            }
        })
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
}