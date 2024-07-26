package com.umc.ttoklip.presentation.honeytip.questionlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.umc.ttoklip.presentation.honeytip.ASK
import com.umc.ttoklip.presentation.honeytip.BOARD
import com.umc.ttoklip.presentation.honeytip.HoneyTipViewModel
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.OnQuestionClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.QuestionListRVA
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadQuestionActivity
import kotlinx.coroutines.launch

class WelfareQuestionListFragment :
    Fragment(),
    OnQuestionClickListener {
    private val questionListRVA by lazy {
        QuestionListRVA(this)
    }
    private val viewModel: HoneyTipViewModel by viewModels(
        ownerProducer = { requireParentFragment().requireParentFragment() }
    )

    lateinit var binding: FragmentHoneyTipListBinding

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
                viewModel.welfareQuestion.collect {
                    questionListRVA.submitList(it)
                }
            }
        }
    }

    fun initView() {
        initRV()
    }

    private fun initRV() {
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rv.adapter = questionListRVA
    }

    override fun onClick(honeyTip: HoneyTipMain) {
        startActivity(ReadQuestionActivity.newIntent(requireContext(), honeyTip.id))
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onPause() {
        super.onPause()
        Log.d("pause", "pause")
        viewModel.resetQuestionList("WELFARE_POLICY")
    }

}