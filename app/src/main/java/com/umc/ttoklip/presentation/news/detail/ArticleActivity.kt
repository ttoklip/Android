package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityArticleBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(R.layout.activity_article) {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModelImpl>()
    override fun initObserver() {
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ARTICLE = "article"
        fun newIntent(context: Context) =
            Intent(context, ArticleActivity::class.java)
    }
}