package com.umc.ttoklip.presentation.news.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentArticleListBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListFragment: BaseFragment<FragmentArticleListBinding>(R.layout.fragment_article_list) {

    private val viewModel: ArticleListViewModel by viewModels<ArticleListViewModelImpl>()

    lateinit var navController: NavController

    override fun initObserver() {
    }

    override fun initView() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        navController = Navigation.findNavController(view)
    }
}