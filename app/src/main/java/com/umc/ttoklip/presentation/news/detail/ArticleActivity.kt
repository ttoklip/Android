package com.umc.ttoklip.presentation.news.detail

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityArticleBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import com.umc.ttoklip.presentation.news.adapter.PostImageRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(R.layout.activity_article) {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModelImpl>()

    private val commentRVA by lazy {
        CommentRVA( { id ->
            viewModel.replyCommentParentId.value = id
        },{ id ,myComment ->
            if (myComment){
                val deleteDialog = DeleteDialogFragment()
                deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                    override fun onClick() {
                        viewModel.deleteComment(id, intent.getIntExtra(ARTICLE,1))
                    }
                })
                deleteDialog.show(supportFragmentManager, deleteDialog.tag)
            }else{
                val reportDialog = ReportDialogFragment()
                reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                    override fun onClick() {
                        viewModel.postReportComment(intent.getIntExtra(ARTICLE,1))
                    }
                })
                reportDialog.show(supportFragmentManager, reportDialog.tag)
            }
        })
    }

    private val imageRVA by lazy {
        PostImageRVA{ imageUrl ->
            val intent = Intent(this, PostImageActivity::class.java)
            intent.putExtra("images", imageUrl.map { it.imageUrl }.toTypedArray())
            startActivity(intent)
        }
    }

    private var isMenuOpen = false
    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.comments.collect {
                    commentRVA.submitList(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toast.collect {
                    if (it.isNotEmpty()){
                        Toast.makeText(baseContext, it,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageUrls.collect {
                    viewModel.imageUrls.collect {
                        imageRVA.submitList(it)
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.replyCommentParentId.collect { id ->
                    if (id == 0) {
                        binding.replyT.text = ""
                    } else {
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
        viewModel.getDetail(intent.getIntExtra(ARTICLE, 0))
        binding.SendCardView.setOnClickListener {
            viewModel.postComment(intent.getIntExtra(ARTICLE, 0))
            binding.commentEt.setText("")
            viewModel.replyCommentParentId.value = 0
        }
        binding.ImgRV.adapter = imageRVA
        binding.dotBtn.setOnClickListener {
            binding.menu.apply {
                if (!isMenuOpen){
                    visibility = View.VISIBLE
                    isMenuOpen = true
                }else{
                    visibility = View.GONE
                    isMenuOpen = false
                }
            }
        }
        binding.menu.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick() {
                    viewModel.postReportNews(intent.getIntExtra(ARTICLE,1),"게시글 신고")
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

    companion object {
        const val ARTICLE = "article"
        fun newIntent(context: Context, id: Int) =
            Intent(context, ArticleActivity::class.java).apply {
                putExtra(ARTICLE, id)
            }
    }
}