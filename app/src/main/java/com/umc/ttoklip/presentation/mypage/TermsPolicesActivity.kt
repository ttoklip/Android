package com.umc.ttoklip.presentation.mypage

import androidx.core.view.isGone
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTermsPolicesBinding
import com.umc.ttoklip.presentation.base.BaseActivity

class TermsPolicesActivity :
    BaseActivity<ActivityTermsPolicesBinding>(R.layout.activity_terms_polices) {
    override fun initView() {
        binding.termsPolicesBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.termsPolices1Btn.setOnClickListener {
            if (binding.termsPolices1Content.isGone) {
                binding.termsPolices1Btn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.termsPolices1Btn.setImageResource(R.drawable.ic_arrow_down_24)
            }
            binding.termsPolices1Content.isGone = binding.termsPolices1Content.isGone.not()
        }
        binding.termsPolices2Btn.setOnClickListener {
            if (binding.termsPolices2Content.isGone) {
                binding.termsPolices2Btn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.termsPolices2Btn.setImageResource(R.drawable.ic_arrow_down_24)
            }
            binding.termsPolices2Content.isGone = binding.termsPolices2Content.isGone.not()
        }
    }

    override fun initObserver() = Unit

}