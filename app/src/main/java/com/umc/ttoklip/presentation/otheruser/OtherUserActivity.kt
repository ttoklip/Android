package com.umc.ttoklip.presentation.otheruser

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityOtheruserProfileBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class OtherUserActivity: BaseActivity<ActivityOtheruserProfileBinding>(R.layout.activity_otheruser_profile) {

    private val viewModel: OtherUserViewModel by viewModels<OtherUserViewModel>()

    override fun initView() {
        binding.vm = viewModel
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