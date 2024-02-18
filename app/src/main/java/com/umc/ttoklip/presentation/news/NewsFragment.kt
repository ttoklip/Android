package com.umc.ttoklip.presentation.news

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.databinding.FragmentNewsBinding
import com.umc.ttoklip.presentation.MainActivity
import com.umc.ttoklip.presentation.alarm.AlarmActivity
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.adapter.Dummy
import com.umc.ttoklip.presentation.news.adapter.NewsCardRVA
import com.umc.ttoklip.presentation.news.adapter.NewsTabAdapter
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels<NewsViewModelImpl>()

    private val vpRVA by lazy {
        NewsCardRVA { news ->
            startActivity(ArticleActivity.newIntent(requireContext(), news.newsletterId))
        }
    }


    private val vpFA by lazy {
        NewsTabAdapter(childFragmentManager, lifecycle)
    }

    override fun initObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomNews.collect {
                    vpRVA.submitList(it)
                }
            }
        }
    }

    override fun initView() {

        binding.vm = viewModel
        viewModel.getMainNews()
        binding.vp.adapter = vpRVA
        binding.indicator.attachTo(binding.vp)

        binding.vp2.adapter = vpFA
        TabLayoutMediator(binding.tabLayout, binding.vp2) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        binding.appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                viewModel.collapsedAppBar()
                (requireActivity() as MainActivity).binding.bottomNav.isGone = true
            } else {
                viewModel.expandedAppBar()
                (requireActivity() as MainActivity).binding.bottomNav.isVisible = true
            }
        })

        binding.searchBtn.setOnClickListener {
            startActivity(SearchActivity.newIntent(requireContext()))
        }
        binding.expandSearchBtn.setOnClickListener {
            startActivity(SearchActivity.newIntent(requireContext()))
        }
        binding.bellBtn.setOnClickListener {
            startActivity(AlarmActivity.newIntent(requireContext()))
        }

        binding.fab.setOnClickListener {
            binding.appBar.setExpanded(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).binding.bottomNav.isVisible = true
    }

    companion object {
        val tabTitleArray = arrayOf(
            "집안일",
            "레시피",
            "안전한생활",
            "복지·정책",
        )
    }
}