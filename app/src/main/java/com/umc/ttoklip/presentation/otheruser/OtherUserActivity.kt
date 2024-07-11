package com.umc.ttoklip.presentation.otheruser

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import com.umc.ttoklip.databinding.ActivityOtheruserProfileBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtherUserActivity :
    BaseActivity<ActivityOtheruserProfileBinding>(R.layout.activity_otheruser_profile) {

    private val viewModel: OtherUserViewModel by viewModels<OtherUserViewModel>()

    override fun initView() {
        binding.vm = viewModel
        (intent.getStringExtra(OTHERUSER) ?: "").let {
            viewModel.getStranger(it)
            if (it == TtoklipApplication.prefs.getString("nickname", "")) {
                binding.reportBtn.isGone = true
            }
        }

        binding.otherprofileBackIb.setOnClickListener {
            finish()
        }
        binding.otherprofileHoneytipCl.setOnClickListener {
            startActivity(
                OtherTipActivity.newIntent(
                    this,
                    viewModel.strangerInfo.value.userId,
                    viewModel.strangerInfo.value.nickname
                )
            )
        }


        binding.reportBtn.setOnClickListener {
            val reportDialog = ReportDialogFragment()
            reportDialog.setDialogClickListener(object : ReportDialogFragment.DialogClickListener {

                override fun onClick(type: String, content: String) {
                    viewModel.postReportUser(
                        com.umc.ttoklip.data.model.news.ReportRequest(
                            content,
                            type
                        )
                    )
                }
            })
            reportDialog.show(supportFragmentManager, reportDialog.tag)
        }
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when(event){
                        is OtherUserViewModel.OtherEvent.ToastMessage -> {
                            Toast.makeText(this@OtherUserActivity, event.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
    }

    companion object {
        const val OTHERUSER = "other_user"
        fun newIntent(context: Context, user: String) =
            Intent(context, OtherUserActivity::class.java).apply {
                putExtra(OTHERUSER, user)
            }
    }
}