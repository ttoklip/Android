package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.graphics.Rect
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import com.umc.ttoklip.databinding.ActivityReadTogetherBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.adapter.TownCommentAdapter
import com.umc.ttoklip.presentation.honeytip.adapter.OnReadImageClickListener
import com.umc.ttoklip.presentation.honeytip.adapter.ReadImageRVA
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Random

@AndroidEntryPoint
class ReadTogetherActivity :
    BaseActivity<ActivityReadTogetherBinding>(R.layout.activity_read_together),
    OnReadImageClickListener {
    private val viewModel: ReadTogetherViewModel by viewModels<ReadTogetherViewModelImpl>()
    private val imageAdapter: ReadImageRVA by lazy {
        ReadImageRVA(this, this@ReadTogetherActivity)
    }
    private val commentRVA by lazy {
        TownCommentAdapter({}, { commentId, reportRequest ->
            if (reportRequest != null) {
                viewModel.reportComment(commentId, reportRequest)
            } else {
                viewModel.deleteComment(commentId)
            }
        })
    }
    private var postId = 0L

    override fun initView() {
        binding.imageRv.adapter = imageAdapter
        binding.commentRv.adapter = commentRVA
        binding.vm = viewModel
        postId = intent.getLongExtra("postId", 0)
        viewModel.savePostId(postId)
        binding.reportBtn.bringToFront()


        binding.deleteBtn.setOnClickListener {
            val deleteDialog = DeleteDialogFragment()
            deleteDialog.setDialogClickListener(object : DeleteDialogFragment.DialogClickListener {
                override fun onClick() {

                }
            })
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.dotBtn.setOnClickListener {
            binding.reportBtn.isVisible = binding.reportBtn.isVisible.not()
        }

        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick(type: String, content: String) {

                }

            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
        val random = Random().nextInt(5)
        val amount = getString(R.string.join_stat_format, random, random + 1)
        val spannableAmount = SpannableString(amount)
        spannableAmount.setSpan(
            ForegroundColorSpan(getColor(R.color.blue)),
            AMOUNT_STRING_START,
            random.toString().length + AMOUNT_STRING_LENGTH,
            SPANNABLE_FLAG_ZERO
        )
        binding.currentJoinStatTv.text = spannableAmount

        binding.cardView.setOnClickListener {
            if (binding.commentEt.text.toString().isNotBlank()) {
                viewModel.createComment(CreateCommentRequest(binding.commentEt.text.toString(), 0L))
            }
        }

    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.joinState.collect {
                        if (it.not()) {
                            binding.joinBtn.background =
                                getDrawable(R.drawable.rectangle_corner_10_strok_1_gray40)
//                            binding.joinBtn.text = getString(R.string.cancel_join)
                        } else {
                            binding.joinBtn.background =
                                getDrawable(R.drawable.yellow_btn_background)
//                            binding.joinBtn.text = getString(R.string.join_together)
                        }
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.postId.collect {
                        viewModel.readTogether(it)
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.postContent.collect { response ->
                        with(binding) {
                            writerTv.text = response.writer
                            titleT.text = response.title
                            contentT.text = response.content
                            imageAdapter.submitList(response.imageUrls.map { url ->
                                ImageUrl(
                                    imageUrl = url.postImageUrl
                                )
                            })
                            commentRVA.submitList(response.commentResponse)

                        }
                    }
                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {

                }
            }
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {

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

    companion object {
        private const val AMOUNT_STRING_START = 0
        private const val SPANNABLE_FLAG_ZERO = 0
        private const val AMOUNT_STRING_LENGTH = 1
    }

    override fun onClick(imageUrl: String) {
        TODO("Not yet implemented")
    }
}