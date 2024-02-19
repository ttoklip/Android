package com.umc.ttoklip.presentation.news.fragment

import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentItemNewsBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.NewsViewModelImpl
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SafeLifeFragment() : BaseFragment<FragmentItemNewsBinding>(R.layout.fragment_item_news) {
    private val parentViewModel: NewsViewModelImpl by viewModels(
        ownerProducer = { requireParentFragment() }
    )
    private val newsRVA by lazy {
        NewsRVA { news ->
                startActivity(ArticleActivity.newIntent(requireContext(), news.newsletterId))
            }

    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.safeLivingList.collect {
                    newsRVA.submitList(it)
                }
            }

        }
    }

    override fun initView() {
        binding.rv.adapter = newsRVA
        binding.sv.getViewTreeObserver().addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            val view = binding.sv.getChildAt(binding.sv.getChildCount() - 1) as View
            val diff: Int = view.bottom - (binding.sv.getHeight() + binding.sv
                .getScrollY())
            if (diff == 0) {
                parentViewModel.getSafePage()
            }
        })
    }
}