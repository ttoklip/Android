package com.umc.ttoklip.presentation.mypage.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogLogoutBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog(private val logout: () -> Unit) :
    BaseDialogFragment<DialogLogoutBinding>(R.layout.dialog_logout) {
    override fun initObserver() {
        binding.logoutAcceptBtn.setOnSingleClickListener {
            logout()
        }
        binding.logoutCancelBtn.setOnSingleClickListener {
            dismiss()
        }
    }

    override fun initView() = Unit
}