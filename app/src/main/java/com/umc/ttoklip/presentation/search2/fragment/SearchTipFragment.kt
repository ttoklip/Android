package com.umc.ttoklip.presentation.search2.fragment

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentItemSearchBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.NewsViewModelImpl
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import com.umc.ttoklip.presentation.search.adapter.SearchRVA
import com.umc.ttoklip.presentation.search2.SearchViewModel2
import com.umc.ttoklip.presentation.search2.SearchViewModelImpl2
import kotlinx.coroutines.launch


class SearchTipFragment() :
    BaseFragment<FragmentItemSearchBinding>(R.layout.fragment_item_search) {
    private val viewModel: SearchViewModel2 by activityViewModels<SearchViewModelImpl2>()


    private val searchRVA by lazy {
        SearchRVA { category, id ->
            when (category) {
                1 -> {
                    startActivity(ArticleActivity.newIntent(requireContext(), id))
                }

                2 -> {}
                3 -> {}
                4 -> {}
                5 -> {}
                else -> {}
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchTipList.collect{
                    searchRVA.submitList(it)
                }
            }
        }
    }

    override fun initView() {
        binding.communicationRv.adapter = searchRVA
        binding.communicationRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalItemViewCount = recyclerView.adapter!!.itemCount - 1

                if (newState == 2 && !recyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == totalItemViewCount
                ) {
                    viewModel.getTipSearch("latest")
                }
            }

        })
    }
}