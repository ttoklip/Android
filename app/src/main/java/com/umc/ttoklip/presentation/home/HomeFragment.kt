package com.umc.ttoklip.presentation.home

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.home.Weather
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.FragmentHomeBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.home.adapter.HomeTipRVA
import com.umc.ttoklip.presentation.hometown.CommunicationActivity
import com.umc.ttoklip.presentation.hometown.TogetherActivity
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTips
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.honeytip.read.ReadHoneyTipActivity
import com.umc.ttoklip.presentation.mypage.adapter.Transaction
import com.umc.ttoklip.presentation.mypage.adapter.TransactionAdapter
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import com.umc.ttoklip.presentation.search2.SearchActivity2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val newsRVA by lazy {
        NewsRVA(onClick = { news ->
            startActivity(ArticleActivity.newIntent(requireContext(), news.newsletterId))
        }
        )
    }
    private val townRVA by lazy {
        TransactionAdapter(requireContext())
    }
    private val tipRVA by lazy {
        HomeTipRVA(this)
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activityBus.collect {
                    when (it) {
                        HomeViewModel.ActivityEventBus.SEARCH -> {
                            startActivity(SearchActivity2.newIntent(requireContext()))
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainData.collect {
                    newsRVA.submitList(it.newsLetters)
                    tipRVA.submitList(it.honeyTips)
                }
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.tipRV.adapter = tipRVA
        viewModel.getMain()

        binding.chatImg.setOnClickListener {
            val intent = Intent(requireContext(), CommunicationActivity::class.java)
            startActivity(intent)
        }
        binding.groupButImg.setOnClickListener {
            val intent = Intent(requireContext(), TogetherActivity::class.java)
            startActivity(intent)
        }
        //테스트
        binding.weatherImg.setImageResource(Weather.CLOUD.resId)
        binding.weatherTitle.text = Weather.CLOUD.label

        binding.newsRV.adapter = newsRVA
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

    override fun onClick(honeyTip: HoneyTipMain) {
        val intent = Intent(activity, ReadHoneyTipActivity::class.java)
        intent.putExtra("postId", honeyTip.id)
        Log.d("Clicked honeyTip", honeyTip.toString())
        Log.d("postId", honeyTip.id.toString())
        startActivity(intent)
    }
}