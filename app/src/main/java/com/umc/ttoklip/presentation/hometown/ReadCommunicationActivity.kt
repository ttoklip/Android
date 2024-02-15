package com.umc.ttoklip.presentation.hometown

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isVisible
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.ActivityReadCommunicationBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.honeytip.DetailHoneyTipFragment
import com.umc.ttoklip.presentation.honeytip.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadCommunicationActivity :
    BaseActivity<ActivityReadCommunicationBinding>(R.layout.activity_read_communication) {
    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailHoneyTipFragment())
            .commit()

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.dotBtn.setOnClickListener {
            binding.reportBtn.isVisible = true
        }

        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {
                override fun onClick(request: ReportRequest) {

                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
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
}