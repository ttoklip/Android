package com.umc.ttoklip.presentation.news.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentArticleListBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.news.adapter.Dummy
import com.umc.ttoklip.presentation.news.adapter.NewsRVA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment :
    BaseFragment<FragmentArticleListBinding>(R.layout.fragment_article_list) {

    private val viewModel: ArticleListViewModel by viewModels<ArticleListViewModelImpl>()

    lateinit var navController: NavController
    private val newsRVA by lazy {
        NewsRVA(onClick = {})
    }

    override fun initObserver() {
    }

    override fun initView() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        navController = Navigation.findNavController(view)
        binding.rv.adapter = newsRVA
        newsRVA.submitList(
            listOf(
                Dummy("1"), Dummy("2"), Dummy("3"), Dummy("4")
            )
        )
        if ((requireActivity() as NewsDetailActivity).goArticle) {
            navController.navigate(R.id.action_articleListFragment_to_articleFragment)
            (requireActivity() as NewsDetailActivity).goArticle = false
        }
    }
}