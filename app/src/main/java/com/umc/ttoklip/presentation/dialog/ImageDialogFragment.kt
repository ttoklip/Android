package com.umc.ttoklip.presentation.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogImageBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment

class ImageDialogFragment: BaseDialogFragment<DialogImageBinding>(R.layout.dialog_image) {
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