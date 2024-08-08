package com.umc.ttoklip.presentation.honeytip.read

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.EditHoneyTip
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.databinding.ActivityReadHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.honeytip.HONEY_TIPS
import com.umc.ttoklip.presentation.honeytip.write.WriteHoneyTipActivity
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import com.umc.ttoklip.presentation.otheruser.OtherUserActivity
import com.umc.ttoklip.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadHoneyTipActivity :
    BaseActivity<ActivityReadHoneyTipBinding>(R.layout.activity_read_honey_tip),
    OnReadImageClickListener {
    private val viewModel: ReadHoneyTipViewModel by viewModels()


    private val commentRVA by lazy {
        CommentRVA(
            this
            ,{ id ->
            viewModel.replyCommentParentId.value = id
        }, { id, myComment ->
            Log.d("mycomment", myComment.toString())
            if (myComment) {
                val deleteDialog = DeleteDialogFragment()
                deleteDialog.setDialogClickListener(object :
                    DeleteDialogFragment.DialogClickListener {
                    override fun onClick() {
                        viewModel.deleteHoneyTipComment(
                            id,
                            postId
                        )
                    }
                })
                deleteDialog.show(supportFragmentManager, deleteDialog.tag)
            } else {
                val reportDialog = ReportDialogFragment()
                reportDialog.setDialogClickListener(object :
                    ReportDialogFragment.DialogClickListener {
                    override fun onClick(type: String, content: String) {
                        viewModel.postReportHoneyTipComment(
                            id,
                            ReportRequest(
                                content = content,
                                reportType = type
                            )
                        )
                    }
                })
                reportDialog.show(supportFragmentManager, reportDialog.tag)
            }
        }, { nick ->
            startActivity(OtherUserActivity.newIntent(this, nick))
        })
    }

    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadHoneyTipActivity)
    }
    private var isShowMenu = false

    // 꿀팁 수정 시 필요
    private var postId = 0
    private var category = ""

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.readEvent.collect {
                    handleReadEvent(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastEvent.collect { text ->
                    showToast(text)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.comments.collect {
                    val list = it.map { it ->
                        NewsCommentResponse(
                            it.commentContent,
                            it.commentId,
                            it.parentId,
                            it.writer,
                            it.writtenTime,
                            it.writerProfileImageUrl
                        )
                    }
                    commentRVA.submitList(list)
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

    private fun handleReadEvent(event: ReadHoneyTipViewModel.ReadEvent) {
        when (event) {
            // 게시글 Data 반영
            is ReadHoneyTipViewModel.ReadEvent.ReadHoneyTipEvent -> {
                val honeyTip = event.inquireHoneyTipResponse
                with(binding) {
                    Glide.with(this@ReadHoneyTipActivity)
                        .load(honeyTip.writerProfileImageUrl)
                        .into(profileImg)
                    titleTv.text = honeyTip.title
                    writerTv.text = honeyTip.writer
                    contentT.text = honeyTip.content
                    linkT.text = honeyTip.urlResponses.firstOrNull()?.urls
                    likeT.text = honeyTip.likeCount.toString()
                    bookmarkT.text = honeyTip.scrapCount.toString()
                    commitT.text = honeyTip.commentCount.toString()
                }

                if (honeyTip.imageUrls.isNotEmpty()) {
                    binding.imageRv.visibility = View.VISIBLE
                    imageAdapter.submitList(honeyTip.imageUrls)
                }

                category = honeyTip.category

                val writer = TtoklipApplication.prefs.getString("nickname", "")
                if (honeyTip.writer == writer) {
                    showHoneyTipWriterMenu()
                } else {
                    showReportBtn()
                }
            }

            else -> {}
        }
    }

    override fun initView() {
        binding.vm = viewModel
        binding.replyT.setOnClickListener {
            viewModel.replyCommentParentId.value = 0
        }

        binding.profileImg.setOnClickListener {
            startActivity(OtherUserActivity.newIntent(this,viewModel.honeyTip.value.writer.toString()))
        }

        postId = intent.getIntExtra("postId", 0)
        Log.d("read postid", postId.toString())

        binding.commentRV.adapter = commentRVA
        binding.SendCardView.setOnClickListener {
            viewModel.postHoneyTipComment(postId)
            binding.commentEt.setText("")
            viewModel.replyCommentParentId.value = 0
        }
        viewModel.inquireHoneyTip(postId)

        binding.backBtn.setOnClickListener {
            finish()
        }
        initImageRVA()
        showDeleteDialog()
        showReportDialog()
        editHoneyTip()
    }

    private fun initImageRVA() {
        binding.imageRv.adapter = imageAdapter
    }

    private fun showReportBtn() {
        binding.dotBtn.setOnClickListener {
            if (!isShowMenu) {
                binding.reportBtn.bringToFront()
                binding.reportBtn.visibility = View.VISIBLE
                isShowMenu = true
            } else {
                binding.reportBtn.visibility = View.GONE
                isShowMenu = false
            }
        }
    }

    private fun showHoneyTipWriterMenu() {
        binding.dotBtn.setOnClickListener {
            if (!isShowMenu) {
                binding.honeyTipMenu.bringToFront()
                binding.honeyTipMenu.visibility = View.VISIBLE
                isShowMenu = true
            } else {
                binding.honeyTipMenu.visibility = View.GONE
                isShowMenu = false
            }
        }
    }

    private fun editHoneyTip() {
        binding.editBtn.setOnClickListener {
            val images = imageAdapter.currentList.filterIsInstance<ImageUrl>()
            val editHoneyTip = EditHoneyTip(
                postId,
                binding.titleTv.text.toString(),
                binding.contentT.text.toString(),
                category,
                images,
                binding.linkT.text.toString()
            )

            startActivity(WriteHoneyTipActivity.editIntent(this, true, HONEY_TIPS, editHoneyTip))
            finish()
        }
    }

    private fun showReportDialog() {
        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {

                override fun onClick(type: String, content: String) {
                    viewModel.reportHoneyTip(
                        postId,
                        ReportRequest(content = content, reportType = type)
                    )
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

    private fun showDeleteDialog() {
        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {
                    viewModel.deleteHoneyTip(postId)
                    finish()
                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val dotBtnRect = Rect()
            val reportBtnRect = Rect()
            val writerMenuRect = Rect()
            binding.dotBtn.getGlobalVisibleRect(dotBtnRect)
            binding.reportBtn.getGlobalVisibleRect(reportBtnRect)
            binding.honeyTipMenu.getGlobalVisibleRect(writerMenuRect)

            if (isShowMenu && !dotBtnRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                if(!reportBtnRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    binding.reportBtn.visibility = View.GONE
                }

                if(!writerMenuRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    binding.honeyTipMenu.visibility = View.GONE
                }
                isShowMenu = false
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onClick(imageUrl: String, position: Int) {
        val images = imageAdapter.currentList.filterIsInstance<ImageUrl>().map { it.imageUrl }
            .toTypedArray()
        startActivity(ReadImageViewActivity.newIntent(this, images, position))
    }

    companion object {
        fun newIntent(context: Context, id: Int) =
            Intent(context, ReadHoneyTipActivity::class.java).apply {
                putExtra("postId", id)
            }
    }
}