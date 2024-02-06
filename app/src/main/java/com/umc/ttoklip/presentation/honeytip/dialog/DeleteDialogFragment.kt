package com.umc.ttoklip.presentation.honeytip.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogDeleteBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment

class DeleteDialogFragment: BaseDialogFragment<DialogDeleteBinding>(R.layout.dialog_delete) {
    private lateinit var dialogClickListener: DialogClickListener
    override fun initObserver() {

    }

    override fun initView() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnClickListener {
            dialogClickListener.onClick()
            dismiss()
        }
    }

    fun setDialogClickListener(dialogClickListener: DialogClickListener){
        this.dialogClickListener = dialogClickListener
    }

    interface DialogClickListener{
        fun onClick()
    }
}