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

                        HomeViewModel.ActivityEventBus.TIP_DETAIL -> {}

                        HomeViewModel.ActivityEventBus.ALARM -> {
                            startActivity(AlarmActivity.newIntent(requireContext()))
                        }

                        HomeViewModel.ActivityEventBus.GROUP_BUY_DETAIL -> {
                            (requireActivity() as MainActivity).goNews()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.newsRV.adapter = newsRVA
        newsRVA.submitList(
            listOf(
                Dummy("1"), Dummy("2"), Dummy("3")
            )
        )
    }

}