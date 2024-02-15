package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isVisible
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityReadCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.dialog.DeleteDialogFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import com.umc.ttoklip.presentation.news.adapter.Comment
import com.umc.ttoklip.presentation.news.adapter.CommentRVA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadCommunicationActivity :
    BaseActivity<ActivityReadCommunicationBinding>(R.layout.activity_read_communication) {
    private val commentRVA by lazy {
        CommentRVA()
    }

    override fun initView() {
        binding.commentRv.adapter = commentRVA
        binding.reportBtn.bringToFront()
        commentRVA.submitList(
            listOf(
                Comment(1, 1, "ㅎㅇ", "ㅎ"),
                Comment(2, 2, "ㅎㅇ", "ㅎ"),
                Comment(3, 1, "ㅎㅇ", "ㅎ")
            )
        )

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.dotBtn.setOnClickListener {
            binding.reportBtn.isVisible = true
        }

        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick() {
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

    }

    override fun initObserver() {

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

    fun showReportDialog() {
        val reportDialog = ReportDialogFragment()
        reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
            override fun onClick() {
            }
        })
        reportDialog.show(supportFragmentManager, reportDialog.tag)
    }
}