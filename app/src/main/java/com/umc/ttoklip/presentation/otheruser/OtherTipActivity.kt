package com.umc.ttoklip.presentation.otheruser

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityOtherHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class OtherTipActivity: BaseActivity<ActivityOtherHoneyTipBinding>(R.layout.activity_other_honey_tip) {

    private val viewModel : OtherTipViewModel by viewModels<OtherTipViewModel>()

    override fun initView() {
        binding.userT.text = intent.getStringExtra(OTHERNAME) ?: "USER"
    }

    override fun initObserver() {}

    companion object {
        const val OTHERID = "other_id"
        const val OTHERNAME = "other_name"
        fun newIntent(context: Context, id: Int, user : String) =
            Intent(context, OtherUserActivity::class.java).apply {
                putExtra(OTHERID, id)
                putExtra(OTHERNAME, user)
            }
    }
}