package com.umc.ttoklip.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentHomeBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.detail.NewsDetailActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.activityBus.collect{
                    when (it) {
                        HomeViewModel.ActivityEventBus.SEARCH -> startActivity(SearchActivity.newIntent(requireContext()))
                        HomeViewModel.ActivityEventBus.NEWS_DETAIL -> startActivity(NewsDetailActivity.newIntent(requireContext()))
                        HomeViewModel.ActivityEventBus.TIP_DETAIL -> {}
                        HomeViewModel.ActivityEventBus.ALARM -> {}
                        else -> {}
                    }
                }
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
    }

}