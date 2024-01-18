package com.umc.ttoklip.presentation.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentNewsBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.home.HomeViewModel
import com.umc.ttoklip.presentation.home.HomeViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment: BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels<NewsViewModelImpl>()
    lateinit var navController: NavController

    override fun initObserver() {
    }

    override fun initView() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
    }
}