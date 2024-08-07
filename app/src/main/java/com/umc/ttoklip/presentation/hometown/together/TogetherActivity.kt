package com.umc.ttoklip.presentation.hometown.together

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.town.Togethers
import com.umc.ttoklip.databinding.ActivityTogetherBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.dialog.TogetherBottomSheetDialogFragment
import com.umc.ttoklip.presentation.hometown.together.read.ReadTogetherActivity
import com.umc.ttoklip.presentation.hometown.together.write.WriteTogetherActivity
import com.umc.ttoklip.presentation.mypage.adapter.OnTogetherItemClickListener
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TogetherActivity : BaseActivity<ActivityTogetherBinding>(R.layout.activity_together),
    OnTogetherItemClickListener {
    private val viewModel: TogetherViewModel by viewModels<TogetherViewModelImpl>()
    private val adapter by lazy {
        TransactionAdapter(this, this)
    }

    override fun initView() {
        binding.vm = viewModel
        binding.writeFab.setOnClickListener {
            val intent = Intent(this, WriteTogetherActivity::class.java)
            startActivity(intent)
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.noticeBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(this))
        }

        binding.togetherRv.adapter = adapter
        binding.togetherRv.layoutManager = LinearLayoutManager(this)

        binding.togetherRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (totalItemCount - lastVisibleItemPosition <= 2) {
                        viewModel.getTogether()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d("start", "start")
    }
    override fun onResume() {
        super.onResume()
        Log.d("resume", "resume")
        viewModel.getTogether()
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.showDialog.collect {
                        if (it) {
                            val sheet = TogetherBottomSheetDialogFragment { filter ->
                                viewModel.getFilters(filter[0], filter[1])
                            }
                            sheet.show(supportFragmentManager, sheet.tag)
                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.mainData.collect {
                        adapter.submitList(it.carts)
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterRequiredAmount.collect {
                        binding.requiredAmount.isGone = false
                        when (it) {
                            1L -> {
                                binding.requiredAmount.text = "10,000"
                            }

                            2L -> {
                                binding.requiredAmount.text = "20,000"
                            }

                            3L -> {
                                binding.requiredAmount.text = "30,000"
                            }

                            4L -> {
                                binding.requiredAmount.text = "40,000"
                            }

                            5L -> {
                                binding.requiredAmount.text = "50,000 이상"
                            }

                            else -> {
                                binding.requiredAmount.isGone = true
                            }
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterMaxMember.collect {
                        binding.maxMemberSizeTv.isGone = false
                        when (it) {
                            1L -> {
                                binding.maxMemberSizeTv.text = "2~4"
                            }

                            2L -> {
                                binding.maxMemberSizeTv.text = "5~7"
                            }

                            3L -> {
                                binding.maxMemberSizeTv.text = "8~10"
                            }

                            4L -> {
                                binding.maxMemberSizeTv.text = "11~13"
                            }

                            5L -> {
                                binding.maxMemberSizeTv.text = "14~17"
                            }

                            6L -> {
                                binding.maxMemberSizeTv.text = "18~20"
                            }

                            else -> {
                                binding.maxMemberSizeTv.isGone = true
                            }
                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.togethers.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun onClick(together: Togethers) {
        val intent = Intent(this, ReadTogetherActivity::class.java)
        intent.putExtra("postId", together.id)
        startActivity(intent)
    }

}