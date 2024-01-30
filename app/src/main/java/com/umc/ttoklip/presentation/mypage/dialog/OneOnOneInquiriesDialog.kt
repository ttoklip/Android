package com.umc.ttoklip.presentation.mypage.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogOneOnOneInquriesBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneOnOneInquiriesDialog :
    BaseDialogFragment<DialogOneOnOneInquriesBinding>(R.layout.dialog_one_on_one_inquries) {
    override fun initObserver() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.acceptBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun initView() = Unit

}