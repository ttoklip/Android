package com.umc.ttoklip.presentation.news

import androidx.fragment.app.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentItemNewsBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.adapter.Dummy
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import com.umc.ttoklip.presentation.news.detail.NewsDetailActivity

class ItemNewsFragment : BaseFragment<FragmentItemNewsBinding>(R.layout.fragment_item_news) {
    private val viewModel: NewsViewModel by viewModels<NewsViewModelImpl>()
    private val newsRVA by lazy {
        NewsRVA { startActivity(NewsDetailActivity.newIntent(requireContext(), true)) }
    }

    override fun initObserver() {

    }

    override fun initView() {
        binding.rv.adapter = newsRVA
        newsRVA.submitList(listOf(
            Dummy("1"), Dummy("2"), Dummy("3"), Dummy("4")
        ))
    }
}