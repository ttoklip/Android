package com.umc.ttoklip.presentation.mypage.dialog

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogOneOnOneInquriesBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneOnOneInquiriesDialog(private val sendListener:(String)->Unit) :
    BaseDialogFragment<DialogOneOnOneInquriesBinding>(R.layout.dialog_one_on_one_inquries) {
    override fun initObserver() {
        binding.cancelBtn.setOnSingleClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnSingleClickListener {
            sendListener(binding.inquiriesContentEt.text.toString())
            dismiss()
        }
    }

    override fun initView() = Unit

}