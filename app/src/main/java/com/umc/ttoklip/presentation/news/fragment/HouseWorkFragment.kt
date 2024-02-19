package com.umc.ttoklip.presentation.news.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentItemNewsBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.NewsViewModel
import com.umc.ttoklip.presentation.news.NewsViewModelImpl
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


class HouseWorkFragment() : BaseFragment<FragmentItemNewsBinding>(R.layout.fragment_item_news) {
    private val parentViewModel: NewsViewModelImpl by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private val newsRVA by lazy {
        NewsRVA { news ->
            startActivity(ArticleActivity.newIntent(requireContext(), news.newsletterId)) }
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.houseWorkList.collect {
                    newsRVA.submitList(it)
                }
            }
        }
    }

    override fun initView() {
        binding.rv.adapter = newsRVA
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalItemViewCount = recyclerView.adapter!!.itemCount - 1

                if (newState == 2 && !recyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == totalItemViewCount
                ) {
                   //viewModel.getSecretCapsulePage()
                }
            }

        })
    }
}