package com.umc.ttoklip.presentation.signup.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup1Binding
import com.umc.ttoklip.generated.callback.OnClickListener
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity

class Signup1Fragment: BaseFragment<FragmentSignup1Binding>(R.layout.fragment_signup1) {

    var sendbutton:Boolean=false
    var nextbutton:Boolean=false

    override fun initObserver() {
    }

    override fun initView() {
        val activity=activity as SignupActivity
        activity?.setProg(1)
        binding.signup1EmailEt.addTextChangedListener {
            val pattern=android.util.Patterns.EMAIL_ADDRESS;
            if(pattern.matcher(it).matches()){
                sendbutton=true
                binding.signup1CertSendButton.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
                binding.signup1CertSendButton.setTextAppearance(R.style.TextAppearance_App_14sp_500)
            }else{
                sendbutton=false
                binding.signup1CertSendButton.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                binding.signup1CertSendButton.setTextAppearance(R.style.TextAppearance_App_14sp_400)
            }
        }
        binding.signup1CertSendButton.setOnClickListener {
            //인증 보내는 로직
            if(sendbutton){
                binding.signup1CertsendTv.visibility=View.VISIBLE
                binding.signup1CertSendButton.text="인증번호 재발송"
            }
        }
        binding.signup1CertEt.addTextChangedListener {
            if(it.toString().equals("111")){
                //인증 확인 로직
                binding.signup1CertnotokTv.visibility=View.GONE
                binding.signup1CertokTv.visibility= View.VISIBLE
                nextbutton=true
                binding.signup1NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
                binding.signup1NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
            }else{
                binding.signup1CertokTv.visibility= View.GONE
                binding.signup1CertnotokTv.visibility=View.VISIBLE
                nextbutton=false
                binding.signup1NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                binding.signup1NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
            }
        }
        binding.signup1NextBtn.setOnClickListener {
            if(nextbutton){
                val bundle= Bundle()
                bundle.putString("userName",binding.signup1NameEt.text.toString())
                bundle.putString("userBirth",binding.signup1BirthEt.text.toString())
                bundle.putString("userEmail",binding.signup1EmailEt.text.toString())
                findNavController().navigate(R.id.action_signup1_fragment_to_signup2_fragment,bundle)
            }
        }
    }
}