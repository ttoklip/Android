package com.umc.ttoklip.presentation.hometown.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogWriteTogetherBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import com.umc.ttoklip.util.setOnSingleClickListener

class TogetherDialog :
    BaseDialogFragment<DialogWriteTogetherBinding>(R.layout.dialog_write_together) {
    private lateinit var dialogClickListener: TogetherDialogClickListener
    override fun initObserver() {

    }

    override fun initView() {
        binding.cancelBtn.setOnSingleClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnSingleClickListener {
            dialogClickListener.onClick()
            dismiss()
        }
    }

    fun setDialogClickListener(dialogClickListener: TogetherDialogClickListener) {
        this.dialogClickListener = dialogClickListener
    }

    interface TogetherDialogClickListener {
        fun onClick()
    }
}