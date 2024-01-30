package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityArticleBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import com.umc.ttoklip.presentation.news.adapter.NewsCardRVA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(R.layout.activity_article) {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModelImpl>()

    private val commentRVA by lazy {
        CommentRVA()
    }
    override fun initObserver() {
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.rv2.adapter = commentRVA
        commentRVA.submitList(listOf(Comment(1,1,"ㅎㅇ","ㅎ"),Comment(2,2,"ㅎㅇ","ㅎ"),Comment(3,1,"ㅎㅇ","ㅎ")))
    }

    companion object {
        const val ARTICLE = "article"
        fun newIntent(context: Context) =
            Intent(context, ArticleActivity::class.java)
    }
}