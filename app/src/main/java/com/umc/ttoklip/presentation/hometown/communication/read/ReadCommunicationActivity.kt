package com.umc.ttoklip.presentation.hometown.communication.read

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.data.model.news.comment.NewsCommentResponse
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.databinding.ActivityReadCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.ImageViewActivity
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadCommunicationActivity :
    BaseActivity<ActivityReadCommunicationBinding>(R.layout.activity_read_communication),
    OnReadImageClickListener {
    private val commentRVA by lazy {
        CommentRVA({ id ->
            viewModel.replyCommentParentId.value = id
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
        })
    }
    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadCommunicationActivity)
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
        binding.replyT.setOnClickListener {
            viewModel.replyCommentParentId.value = 0
        }
        binding.commentRv.adapter = commentRVA
        binding.reportBtn.bringToFront()
        postId = intent.getLongExtra("postId", 0)
        viewModel.savePostId(postId)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick(type: String, content: String) {
                    viewModel.reportPost(ReportRequest(content = content, reportType = type))
                }

            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }

        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {
                    viewModel.deleteCommunication()
                    finish()
                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }

        binding.SendCardView.setOnClickListener {
            if (binding.commentEt.text.toString().isNotBlank()) {
                viewModel.createComment()
            }
            binding.commentEt.setText("")
            viewModel.replyCommentParentId.value = 0
        }


        binding.editBtn.setOnClickListener {

        }
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

    private fun showWriterMenu() {
        binding.dotBtn.setOnClickListener {
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
                            writerTv.text = response.writer
                            titleTv.text = response.title
                            contentT.text = response.content


                            Log.d("image", response.imageUrls.toString())
                            imageAdapter.submitList(response.imageUrls.map { url ->
                                ImageUrl(url.imageUrl)
                            })
                            if (response.writer == "  1") {
                                communityMenu.bringToFront()
                            }
                            likeT.text = response.likeCount.toString()
                            bookmarkT.text = response.scrapCount.toString()
                            commitT.text = response.commentCount.toString()

                            if(response.writer == TtoklipApplication.prefs.getString("nickname","")){
                                showWriterMenu()
                            } else{
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
                                it.parentId.toInt(),
                                it.writer,
                                it.writtenTime
                            )
                        }
                        commentRVA.submitList(list)
                    }
                }
            }

            launch {
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

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.toast.collect {
                        Toast.makeText(this@ReadCommunicationActivity, it, Toast.LENGTH_SHORT)
                            .show()
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

    override fun onClick(imageUrl: String) {
        val images = imageAdapter.currentList.filterIsInstance<ImageUrl>().map { it.imageUrl }
            .toTypedArray()
        Log.d("images", images.toString())
        val intent = Intent(this, ImageViewActivity::class.java)
        intent.putExtra("images", images)
        startActivity(intent)
    }

    companion object {
        const val COMMUNICATION = "postId"
        fun newIntent(context: Context, id: Long) =
            Intent(context, ReadCommunicationActivity::class.java).apply {
                putExtra(COMMUNICATION, id)
            }
    }
}