package com.umc.ttoklip.presentation.news.detail

import android.os.Bundle
import android.view.View
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentArticleListBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment: BaseFragment<FragmentArticleListBinding>(R.layout.fragment_article_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}