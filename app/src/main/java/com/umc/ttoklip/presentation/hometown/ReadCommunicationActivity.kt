package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.ImageUrl
import com.umc.ttoklip.data.model.town.CreateCommentRequest
import com.umc.ttoklip.data.model.town.ReportRequest
import com.umc.ttoklip.databinding.ActivityReadCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.TownCommentAdapter
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReadCommunicationActivity :
    BaseActivity<ActivityReadCommunicationBinding>(R.layout.activity_read_communication),
    OnReadImageClickListener {
    private val commentRVA by lazy {
        TownCommentAdapter({}, { commentId, reportRequest ->
            if (reportRequest != null) {
                viewModel.reportComment(commentId, reportRequest)
            } else {
                viewModel.deleteComment(commentId)
            }
        })
    }
    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadCommunicationActivity)
    }
    private val viewModel: ReadCommunicationViewModel by viewModels<ReadCommunicationViewModelImpl>()
    private var postId = 0L

    override fun initView() {
        binding.imageRv.adapter = imageAdapter
        binding.commentRv.adapter = commentRVA
        binding.reportBtn.bringToFront()
        postId = intent.getLongExtra("postId", 0)
        viewModel.savePostId(postId)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.dotBtn.setOnClickListener {
            binding.reportBtn.isVisible = true
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

                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }

        binding.cardView.setOnClickListener {
            if (binding.commentEt.text.toString().isNotBlank()) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.createComment(
                        CreateCommentRequest(
                            binding.commentEt.text.toString(),
                            0L
                        )
                    )
                    delay(500)
                    viewModel.readCommunication(postId)
                }
            }
        }

        binding.scrapBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.changeScrap()
                delay(500)
                viewModel.readCommunication(postId)
            }
        }
        binding.likeBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.changeLike()
                delay(500)
                viewModel.readCommunication(postId)
            }
        }

        binding.editBtn.setOnClickListener {
            
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
                            if (response.likedByCurrentUser) {
                                likeImg.setImageDrawable(getDrawable(R.drawable.ic_heart_on_20))
                            } else {
                                likeImg.setImageDrawable(getDrawable(R.drawable.ic_heart_off_20))
                            }
                            if (response.scrapedByCurrentUser) {
                                bookmarkImg.setImageDrawable(getDrawable(R.drawable.ic_bookmark_on_20))
                            } else {
                                bookmarkImg.setImageDrawable(getDrawable(R.drawable.ic_bookmark_off_20))
                            }
//                            Log.d("comment", response.commentResponse.toString())
                            commentRVA.submitList(response.commentResponses)
                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.like.collect {
                        if (it) {
                            binding.likeImg.setImageDrawable(getDrawable(R.drawable.ic_heart_on_20))
                        } else {
                            binding.likeImg.setImageDrawable(getDrawable(R.drawable.ic_heart_off_20))
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.scrap.collect {
                        if (it) {
                            binding.bookmarkImg.setImageDrawable(getDrawable(R.drawable.ic_bookmark_on_20))
                        } else {
                            binding.bookmarkImg.setImageDrawable(getDrawable(R.drawable.ic_bookmark_off_20))
                        }
                    }
                }
            }


            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.scrap.collect {

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

    }

    companion object {
        const val COMMUNICATION = "postId"
        fun newIntent(context: Context, id: Int) =
            Intent(context, ReadCommunicationActivity::class.java).apply {
                putExtra(COMMUNICATION, id)
            }
    }
}