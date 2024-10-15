package com.umc.ttoklip.presentation.hometown.communication.read

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.town.EditCommunication
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.databinding.ActivityReadCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.read.ReadImageViewActivity
import com.umc.ttoklip.presentation.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.hometown.communication.write.WriteCommunicationActivity
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import com.umc.ttoklip.presentation.otheruser.OtherUserActivity
import com.umc.ttoklip.util.setOnSingleClickListener
import com.umc.ttoklip.util.showKeyboard
import com.umc.ttoklip.util.showToast
import com.umc.ttoklip.util.toReplyNicknameFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadCommunicationActivity :
    BaseActivity<ActivityReadCommunicationBinding>(R.layout.activity_read_communication),
    com.umc.ttoklip.presentation.hometown.adapter.OnReadImageClickListener {
    private val commentRVA by lazy {
        CommentRVA(
            this,
            { id, name ->
                viewModel.replyCommentParentId.value = Pair(id, name.toReplyNicknameFormat())
                binding.commentEt.showKeyboard()
                binding.scrollV.viewTreeObserver.addOnGlobalLayoutListener {
                    binding.scrollV.smoothScrollTo(0, binding.scrollV.getChildAt(0).bottom)
                }
            }, { id, myComment ->
                if (myComment) {
                    val deleteDialog = DeleteDialogFragment()
                    deleteDialog.setDialogClickListener(object :
                        DeleteDialogFragment.DialogClickListener {
                        override fun onClick() {
                            viewModel.deleteComment(id.toLong())
                        }
                    })
                    deleteDialog.show(supportFragmentManager, deleteDialog.tag)
                } else {

                    val reportDialog = ReportDialogFragment()
                    reportDialog.setDialogClickListener(object :
                        ReportDialogFragment.DialogClickListener {
                        override fun onClick(type: String, content: String) {
                            viewModel.reportComment(
                                id.toLong(),
                                com.umc.ttoklip.data.model.honeytip.request.ReportRequest(
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
    private val imageAdapter: com.umc.ttoklip.presentation.hometown.adapter.ReadImageRVA by lazy {
        com.umc.ttoklip.presentation.hometown.adapter.ReadImageRVA(
            this,
            this@ReadCommunicationActivity
        )
    }
    private val viewModel: ReadCommunicationViewModel by viewModels<ReadCommunicationViewModelImpl>()
    private var postId = 0L
    private var isShowMenu = false

    override fun initView() {
        binding.vm = viewModel
        binding.imageRv.apply {
            adapter = imageAdapter
            itemAnimator = null
        }
        binding.replyT.setOnSingleClickListener {
            viewModel.replyCommentParentId.value = Pair(0, "")
        }
        binding.commentRv.adapter = commentRVA
        binding.reportBtn.bringToFront()
        postId = intent.getLongExtra("postId", 0)
        viewModel.savePostId(postId)

        binding.backBtn.setOnSingleClickListener {
            finish()
        }

        binding.commentEt.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN && binding.commentEt.text.isEmpty()) {
                viewModel.replyCommentParentId.value = Pair(0, "")
                true
            } else {
                false
            }
        }

        binding.reportBtn.setOnSingleClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick(type: String, content: String) {
                    viewModel.reportPost(ReportRequest(content = content, reportType = type))
                }

            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }

        binding.deleteBtn.setOnSingleClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {
                    viewModel.deleteCommunication()
                    finish()
                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }

        binding.SendCardView.setOnSingleClickListener {
            viewModel.createComment()
            binding.commentEt.setText("")
            viewModel.replyCommentParentId.value = Pair(0, "")
        }


        binding.editBtn.setOnSingleClickListener {
            val images =
                imageAdapter.currentList.filterIsInstance<com.umc.ttoklip.data.model.town.ImageUrl>()
            Log.d("editImages", images.toString())
            startActivity(
                WriteCommunicationActivity.newIntent(
                    this, EditCommunication(
                        postId,
                        binding.titleTv.text.toString(),
                        binding.contentT.text.toString(),
                        images
                    )
                )
            )
            finish()
        }
    }

    private fun showReportBtn() {
        binding.dotBtn.setOnSingleClickListener {
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

    private fun showWriterMenu() {
        binding.dotBtn.setOnSingleClickListener {
            if (!isShowMenu) {
                binding.communityMenu.bringToFront()
                binding.communityMenu.visibility = View.VISIBLE
                isShowMenu = true
            } else {
                binding.communityMenu.visibility = View.GONE
                isShowMenu = false
            }
        }
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.postId.collect {
                        if (it != 0L) {
                            viewModel.readCommunication(it)
                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.postContent.collect { response ->
                        with(binding) {
                            Glide.with(this@ReadCommunicationActivity)
                                .load(response.userProfileImageUrl)
                                .placeholder(R.drawable.ic_defeault_logo)
                                .into(binding.profileImg)
                            writerTv.text = response.writer
                            titleTv.text = response.title
                            contentT.text = response.content


                            Log.d("image", response.imageUrls.toString())
                            imageAdapter.submitList(response.imageUrls.map { url ->
                                com.umc.ttoklip.data.model.town.ImageUrl(
                                    url.communityImageId,
                                    url.communityImageUrl
                                )
                            })
                            if (response.writer == "  1") {
                                communityMenu.bringToFront()
                            }
                            likeT.text = response.likeCount.toString()
                            bookmarkT.text = response.scrapCount.toString()
                            commitT.text = response.commentCount.toString()

                            if (response.writer == TtoklipApplication.prefs.getString(
                                    "nickname",
                                    ""
                                )
                            ) {
                                showWriterMenu()
                            } else {
                                showReportBtn()
                            }
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.comments.collect {
                        val list = it.map { it ->
                            NewsCommentResponse(
                                it.commentContent,
                                it.commentId.toInt(),
                                it.parentId?.toInt(),
                                it.writer,
                                it.writtenTime,
                                it.writerProfileImageUrl
                            )
                        }
                        commentRVA.submitList(list)
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.replyCommentParentId.collect { (id, name) ->
                        if (id == 0) {
                            binding.commentEt.setText("")
                        } else {
                            val spannableString = SpannableString(name).apply {
                                setSpan(
                                    ForegroundColorSpan(
                                        ContextCompat.getColor(
                                            this@ReadCommunicationActivity,
                                            R.color.blue
                                        )
                                    ),
                                    0, name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                            binding.commentEt.setText(spannableString)
                            binding.commentEt.setSelection(name.length)
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.toast.collect {
                        showToast(it)
                    }
                }
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onClick(imageUrl: String, position: Int) {
        val images =
            imageAdapter.currentList.filterIsInstance<com.umc.ttoklip.data.model.town.ImageUrl>()
                .map { it.communityImageUrl }
                .toTypedArray()
        startActivity(ReadImageViewActivity.newIntent(this, images, position))
    }

    companion object {
        const val COMMUNICATION = "postId"
        fun newIntent(context: Context, id: Long) =
            Intent(context, ReadCommunicationActivity::class.java).apply {
                putExtra(COMMUNICATION, id)
            }
    }
}