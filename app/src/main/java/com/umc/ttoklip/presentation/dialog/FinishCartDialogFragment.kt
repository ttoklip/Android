package com.umc.ttoklip.presentation.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogCartFinishBinding
import com.umc.ttoklip.databinding.DialogDeleteBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment

class FinishCartDialogFragment: BaseDialogFragment<DialogCartFinishBinding>(R.layout.dialog_cart_finish) {
    private var dialogClickListener: DialogClickListener? = null
    override fun initObserver() {

    }

    override fun initView() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnClickListener {
            dialogClickListener?.onClick()
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