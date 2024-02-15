package com.umc.ttoklip.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentHomeBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.home.adapter.HomeTipRVA
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import com.umc.ttoklip.presentation.mypage.adapter.Transaction
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import com.umc.ttoklip.presentation.news.adapter.Dummy
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val newsRVA by lazy {
        NewsRVA(onClick = {
            startActivity(ArticleActivity.newIntent(requireContext()))
        }
        )
    }
    private val townRVA by lazy {
        TransactionAdapter(requireContext())
    }
    private val tipRVA by lazy {
        HomeTipRVA({})
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activityBus.collect {
                    when (it) {
                        HomeViewModel.ActivityEventBus.SEARCH -> {
                            startActivity(SearchActivity.newIntent(requireContext()))
                        }

                        HomeViewModel.ActivityEventBus.NEWS_DETAIL -> {
                            (requireActivity() as MainActivity).goNews()
                        }

                        HomeViewModel.ActivityEventBus.TIP_DETAIL -> {
                            (requireActivity() as MainActivity).goTip()
                        }

                        HomeViewModel.ActivityEventBus.ALARM -> {
                            startActivity(AlarmActivity.newIntent(requireContext()))
                        }

                        HomeViewModel.ActivityEventBus.GROUP_BUY_DETAIL -> {
                            (requireActivity() as MainActivity).goTown()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.tipRV.adapter = tipRVA
        tipRVA.submitList(
            listOf(
                HoneyTips(
                    "똑똑이",
                    "음식물 쓰레기 냄새 방지!!",
                    "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                    "1일전",
                    0
                ),
                HoneyTips(
                    "똑똑이",
                    "음식물 쓰레기 냄새 방지!!",
                    "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                    "1일전",
                    0
                ),
                HoneyTips(
                    "똑똑이",
                    "음식물 쓰레기 냄새 방지!!",
                    "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...",
                    "1일전",
                    0
                )
            )
        )
        binding.newsRV.adapter = newsRVA
        newsRVA.submitList(
            listOf(
                Dummy("1"), Dummy("2"), Dummy("3")
            )
        )
        binding.groupBuyRV.adapter = townRVA
        townRVA.submitList(
            listOf(
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
                    currentAmount = 36000,
                    targetAmount = 36000,
                    currentMember = 5,
                    targetMember = 5,
                    commentAmount = 14,
                    closureReason = "마감"
                )
            )
        )
    }

}