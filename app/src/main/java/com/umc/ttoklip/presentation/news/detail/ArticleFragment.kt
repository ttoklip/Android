package com.umc.ttoklip.presentation.news.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentArticleBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.home.HomeViewModel
import com.umc.ttoklip.presentation.home.HomeViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModelImpl>()
    override fun initObserver() {
    }

    override fun initView() {
        binding.vm = viewModel
    }
}