package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityArticleBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(R.layout.activity_article) {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModelImpl>()

    private val commentRVA by lazy {
        CommentRVA { id ->
            viewModel.replyCommentParentId.value = id
        }
    }
    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.comments.collect{
                    commentRVA.submitList(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.replyCommentParentId.collect{ id ->
                    if (id == 0){
                        binding.replyT.text = ""
                    }else {
                        binding.replyT.text = "@${id}"
                    }
                }
            }
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.replyT.setOnClickListener {
            viewModel.replyCommentParentId.value = 0
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.commentRV.adapter = commentRVA
        viewModel.getDetail(intent.getIntExtra(ARTICLE,0))
        binding.SendCardView.setOnClickListener {
            viewModel.postComment(intent.getIntExtra(ARTICLE,0))
            binding.commentEt.setText("")
            viewModel.replyCommentParentId.value = 0
        }
    }

    companion object {
        const val ARTICLE = "article"
        fun newIntent(context: Context, id : Int) =
            Intent(context, ArticleActivity::class.java).apply {
                putExtra(ARTICLE , id)
            }
    }
}