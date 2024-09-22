package com.umc.ttoklip.presentation.mypage

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.view.isGone
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityCustomerServiceCenterBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.dialog.OneOnOneInquiriesDialog
import com.umc.ttoklip.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerServiceCenterActivity :
    BaseActivity<ActivityCustomerServiceCenterBinding>(R.layout.activity_customer_service_center) {
    override fun initView() {
        binding.customerServiceCenterBackBtn.setOnSingleClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.faqSub1Btn.setOnSingleClickListener {
            if (binding.faqSub1Content.isGone) {
                binding.faqSub1Btn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.faqSub1Btn.setImageResource(R.drawable.ic_arrow_down_24)
            }
            binding.faqSub1Content.isGone = binding.faqSub1Content.isGone.not()
        }
        binding.faqSub2Btn.setOnSingleClickListener {
            if (binding.faqSub2Content.isGone) {
                binding.faqSub2Btn.setImageResource(R.drawable.ic_arrow_up_24)
            } else {
                binding.faqSub2Btn.setImageResource(R.drawable.ic_arrow_down_24)
            }
            binding.faqSub2Content.isGone = binding.faqSub2Content.isGone.not()
        }
        binding.oneOnOneInquriesBtn.setOnSingleClickListener {
            val dialog = OneOnOneInquiriesDialog{content->
                sendEmail(content)
            }
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    override fun initObserver() = Unit

    @SuppressLint("QueryPermissionsNeeded")
    private fun sendEmail(content:String){
        val email = getString(R.string.contact_email)
        Log.d("sendEmail", "Email address: $email")
        val intent= Intent(Intent.ACTION_SENDTO).apply {
            type="text/plain"
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL,arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT,"[똑립] 1:1 문의")
            putExtra(Intent.EXTRA_TEXT,content)
        }
        if(intent.resolveActivity(packageManager)!=null){
            startActivity(Intent.createChooser(intent,"메일 전송"))
        }else{
            Toast.makeText(this,"메일 전송 실패",Toast.LENGTH_LONG).show()
        }
    }
}