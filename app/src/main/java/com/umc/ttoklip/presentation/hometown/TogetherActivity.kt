package com.umc.ttoklip.presentation.hometown

import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTogetherBinding
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.dialog.TogetherBottomSheetDialogFragment
import com.umc.ttoklip.presentation.mypage.adapter.Transaction
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TogetherActivity : BaseActivity<ActivityTogetherBinding>(R.layout.activity_together) {
    private val viewModel: TogetherViewModel by viewModels<TogetherViewModelImpl>()
    private val adapter by lazy {
        TransactionAdapter(this)
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.noticeBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(this))
        }


        val dummy = listOf(
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            ),
            Transaction(
                title = "같이 햇반 대량 구매하실 분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 13000,
                targetAmount = 36000,
                currentMember = 1,
                targetMember = 5,
                commentAmount = 4,
                closureReason = null
            ),
            Transaction(
                title = "분?",
                date = "1일전",
                ownerId = "똑똑이",
                address = "서울 어딘가",
                currentAmount = 36000,
                targetAmount = 36000,
                currentMember = 5,
                targetMember = 5,
                commentAmount = 14,
                closureReason = "마감"
            )
        )
        binding.togetherRv.adapter = adapter
        binding.togetherRv.layoutManager = LinearLayoutManager(this)
        adapter.submitList(dummy)
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.showDialog.collect {
                        if (it) {
                            val sheet = TogetherBottomSheetDialogFragment { filter ->
                                viewModel.getFilters(filter[0], filter[1], filter[2], filter[3])
                            }
                            sheet.show(supportFragmentManager, sheet.tag)
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterSort.collect {
                        binding.sortfilterTv.isGone = false
                        when (it) {
                            1 -> {
                                binding.sortfilterTv.text = "최신순"
                            }

                            2 -> {
                                binding.sortfilterTv.text = "인기순"
                            }

                            3 -> {
                                binding.sortfilterTv.text = "댓글많은순"
                            }

                            else -> {
                                binding.sortfilterTv.isGone = true
                            }
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterDuration.collect {
                        binding.durationFilter.isGone = false
                        when (it) {
                            1 -> {
                                binding.durationFilter.text = "1시간"
                            }

                            2 -> {
                                binding.durationFilter.text = "3시간"
                            }

                            3 -> {
                                binding.durationFilter.text = "6시간"
                            }

                            4 -> {
                                binding.durationFilter.text = "12시간"
                            }

                            5 -> {
                                binding.durationFilter.text = "1일"
                            }

                            6 -> {
                                binding.durationFilter.text = "3일"
                            }

                            7 -> {
                                binding.durationFilter.text = "1주일"
                            }

                            else -> {
                                binding.durationFilter.isGone = true
                            }
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterRequiredAmount.collect {
                        binding.requiredAmount.isGone = false
                        when (it) {
                            1 -> {
                                binding.requiredAmount.text = "10,000"
                            }

                            2 -> {
                                binding.requiredAmount.text = "20,000"
                            }

                            3 -> {
                                binding.requiredAmount.text = "30,000"
                            }

                            4 -> {
                                binding.requiredAmount.text = "40,000"
                            }

                            5 -> {
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
                            1 -> {
                                binding.maxMemberSizeTv.text = "2~4"
                            }

                            2 -> {
                                binding.maxMemberSizeTv.text = "5~7"
                            }

                            3 -> {
                                binding.maxMemberSizeTv.text = "8~10"
                            }

                            4 -> {
                                binding.maxMemberSizeTv.text = "11~13"
                            }

                            5 -> {
                                binding.maxMemberSizeTv.text = "14~17"
                            }

                            6 -> {
                                binding.maxMemberSizeTv.text = "18~20"
                            }

                            else -> {
                                binding.maxMemberSizeTv.isGone = true
                            }
                        }
                    }
                }
            }
        }
    }

}