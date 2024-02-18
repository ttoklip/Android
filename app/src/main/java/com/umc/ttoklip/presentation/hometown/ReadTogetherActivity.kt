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
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.ActivityReadTogetherBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random

@AndroidEntryPoint
class ReadTogetherActivity :
    BaseActivity<ActivityReadTogetherBinding>(R.layout.activity_read_together) {
    private val viewModel: ReadTogetherViewModel by viewModels<ReadTogetherViewModelImpl>()
    private val commentRVA by lazy {
        CommentRVA()
    }

    override fun initView() {
        binding.vm = viewModel
        binding.reportBtn.bringToFront()
        binding.commentRv.adapter = commentRVA
        commentRVA.submitList(
            listOf(
                Comment(1, 1, "ㅎㅇ", "ㅎ"),
                Comment(2, 2, "ㅎㅇ", "ㅎ"),
                Comment(3, 1, "ㅎㅇ", "ㅎ")
            )
        )

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
//        if (currentAmount == targetAmount) {
//            spannableAmount.setSpan(
//                ForegroundColorSpan(context.getColor(R.color.blue)),
//                TransactionAdapter.AMOUNT_STRING_START,
//                data.currentAmount.toString().length + TransactionAdapter.AMOUNT_STRING_LENGTH,
//                TransactionAdapter.SPANNABLE_FLAG_ZERO
//            )
//        } else {
//            spannableAmount.setSpan(
//                ForegroundColorSpan(context.getColor(R.color.orange)),
//                TransactionAdapter.AMOUNT_STRING_START,
//                data.currentAmount.toString().length + TransactionAdapter.AMOUNT_STRING_LENGTH,
//                TransactionAdapter.SPANNABLE_FLAG_ZERO
//            )
//        }

    }

    override fun initObserver() {
//        with(lifecycleScope) {
//            launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    viewModel.joinState.collect {
//                        if (it.not()) {
//                            binding.joinBtn.background =
//                                getDrawable(R.drawable.rectangle_corner_10_strok_1_gray40)
////                            binding.joinBtn.text = getString(R.string.cancel_join)
//                        } else {
//                            binding.joinBtn.background =
//                                getDrawable(R.drawable.yellow_btn_background)
////                            binding.joinBtn.text = getString(R.string.join_together)
//                        }
//                    }
//                }
//            }
//        }
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
}