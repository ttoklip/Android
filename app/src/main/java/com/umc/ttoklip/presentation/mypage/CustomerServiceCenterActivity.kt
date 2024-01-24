package com.umc.ttoklip.presentation.mypage

import androidx.core.view.isGone
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityCustomerServiceCenterBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.dialog.OneOnOneInquiriesDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerServiceCenterActivity :
    BaseActivity<ActivityCustomerServiceCenterBinding>(R.layout.activity_customer_service_center) {
    override fun initView() {
        binding.customerServiceCenterBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.faqSub1Btn.setOnClickListener {
            if (binding.faqSub1Content.isGone) {
                binding.faqSub1Btn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.faqSub1Btn.setImageResource(R.drawable.ic_arrow_down_24)
            }
            binding.faqSub1Content.isGone = binding.faqSub1Content.isGone.not()
        }
        binding.faqSub2Btn.setOnClickListener {
            if (binding.faqSub2Content.isGone) {
                binding.faqSub2Btn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.faqSub2Btn.setImageResource(R.drawable.ic_arrow_down_24)
            }
            binding.faqSub2Content.isGone = binding.faqSub2Content.isGone.not()
        }
        binding.oneOnOneInquriesBtn.setOnClickListener {
            val dialog = OneOnOneInquiriesDialog()
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    override fun initObserver() = Unit
}