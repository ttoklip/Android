package com.umc.ttoklip.presentation.news.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentItemNewsBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.NewsViewModelImpl
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import com.umc.ttoklip.presentation.news.detail.ArticleActivity
import kotlinx.coroutines.launch


class HouseWorkFragment() :Fragment() {
    private val parentViewModel: NewsViewModelImpl by viewModels(
        ownerProducer = { requireParentFragment() }
    )


    lateinit var binding: FragmentItemNewsBinding

    private val newsRVA by lazy {
        NewsRVA { news ->
            startActivity(ArticleActivity.newIntent(requireContext(), news.newsletterId))
        }
    }

    fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.houseWorkList.collect {
                    newsRVA.submitList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_item_news,
            null,
            false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    fun initView() {
        binding.rv.adapter = newsRVA
        binding.sv.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            val view: View? = binding.sv.getChildAt(binding.sv.childCount - 1)
            if (view != null) {
                val diff: Int = view.bottom - (binding.sv.height + binding.sv
                    .scrollY)
                if (diff == 0) {
                    Log.d("end", "end")
                    parentViewModel.getHousePage()
                }
            }
        })

    }
}