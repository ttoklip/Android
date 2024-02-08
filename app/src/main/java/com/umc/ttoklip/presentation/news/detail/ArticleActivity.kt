package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityArticleBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import com.umc.ttoklip.presentation.news.adapter.NewsCardRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(R.layout.activity_article) {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModelImpl>()

    private val commentRVA by lazy {
        CommentRVA()
    }
    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.comments.collect{
                    commentRVA.submitList(it)
                }
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.commentRV.adapter = commentRVA
        viewModel.getDetail(intent.getIntExtra(ARTICLE,0))
    }

    companion object {
        const val ARTICLE = "article"
        fun newIntent(context: Context, id : Int) =
            Intent(context, ArticleActivity::class.java).apply {
                putExtra(ARTICLE , id)
            }
    }
}