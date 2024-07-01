package com.umc.ttoklip.presentation.otheruser

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.ActivityOtheruserProfileBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherUserActivity :
    BaseActivity<ActivityOtheruserProfileBinding>(R.layout.activity_otheruser_profile) {

    private val viewModel: OtherUserViewModel by viewModels<OtherUserViewModel>()

    override fun initView() {
        binding.vm = viewModel
        viewModel.getStranger(intent.getStringExtra(OTHERUSER) ?: "")

        binding.otherprofileBackIb.setOnClickListener {
            finish()
        }
        binding.otherprofileHoneytipCl.setOnClickListener {
            startActivity(OtherTipActivity.newIntent(this, viewModel.strangerInfo.value.userId, viewModel.strangerInfo.value.nickname))
        }

        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {

                override fun onClick(type: String, content: String) {
                    //신고하기 api 예정

                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

    override fun initObserver() {

    }

    companion object {
        const val OTHERUSER = "other_user"
        fun newIntent(context: Context, user: String) =
            Intent(context, OtherUserActivity::class.java).apply {
                putExtra(OTHERUSER, user)
            }
    }
}