package com.umc.ttoklip.presentation.mypage.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogLogoutBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog : BaseDialogFragment<DialogLogoutBinding>(R.layout.dialog_logout) {
    override fun initObserver() {
        binding.logoutAcceptBtn.setOnClickListener {
            dismiss()
        }
        binding.logoutCancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun initView() = Unit
}