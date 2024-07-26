package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.utils.Utils
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup1Binding
import com.umc.ttoklip.generated.callback.OnClickListener
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import com.umc.ttoklip.presentation.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Signup1Fragment: BaseFragment<FragmentSignup1Binding>(R.layout.fragment_signup1) {

    var sendbutton:Boolean=false
    var sendcertbutton:Boolean=false
    var nextbutton:Boolean=false
    private lateinit var vm: SignupViewModel

    override fun initObserver() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.verifycheckok.collect{
                        if(it){
                            delay(200)
                            vm.verifySuccess.collect{
                                if (it) {
                                    binding.signup1CertnotokTv.visibility=View.GONE
                                    binding.signup1CertokTv.visibility= View.VISIBLE
                                } else {
                                    binding.signup1CertokTv.visibility= View.GONE
                                    binding.signup1CertnotokTv.visibility=View.VISIBLE
                                }
                                nextCheck()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initView() {
        vm=ViewModelProvider(requireActivity()).get(SignupViewModel::class.java)

        val activity=activity as SignupActivity
        activity?.setProg(1)

        initValue()

        binding.signup1NameEt.addTextChangedListener {
            nextCheck()
        }
//        binding.signup1BirthEt.addTextChangedListener {
//            nextCheck()
//        }
        binding.signup1EmailEt.addTextChangedListener {
            //유효한 이메일 값인지 체크
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
            vm.verifySuccess.value=false
            sendcertbutton=false
            binding.signup1CertCheckButton.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
            binding.signup1CertCheckButton.setTextAppearance(R.style.TextAppearance_App_14sp_400)
            nextCheck()
        }
        binding.signup1CertEt.addTextChangedListener {
            if(binding.signup1CertEt.text.isNotEmpty()){
                sendcertbutton=true
                binding.signup1CertCheckButton.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
                binding.signup1CertCheckButton.setTextAppearance(R.style.TextAppearance_App_14sp_500)
            }else{
                sendcertbutton=false
                binding.signup1CertCheckButton.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                binding.signup1CertCheckButton.setTextAppearance(R.style.TextAppearance_App_14sp_400)
            }
            nextCheck()
        }

        binding.signup1CertSendButton.setOnClickListener {
            //인증 보내는 로직
            if(sendbutton){
                binding.signup1CertsendTv.visibility=View.VISIBLE
                binding.signup1CertSendButton.text="인증번호 재발송"
                vm.verifySend(binding.signup1EmailEt.text.toString())
            }
        }
        binding.signup1CertCheckButton.setOnClickListener {
            if(sendcertbutton){
                vm.verifyCheck(binding.signup1EmailEt.text.toString(),binding.signup1CertEt.text.toString())
                vm.verifyCheckOk()

                context?.hideKeybord(binding.signup1CertEt)
            }
        }

        binding.signup1NextBtn.setOnClickListener {
            if(nextbutton){
                vm.name.value=binding.signup1NameEt.text.toString()
//                vm.birth.value=binding.signup1BirthEt.text.toString()
                vm.email.value=binding.signup1EmailEt.text.toString()
                findNavController().navigate(R.id.action_signup1_fragment_to_signup2_fragment)
            }
        }
    }

    private fun initValue() {
        binding.signup1NameEt.setText(vm.name.value)
//        binding.signup1BirthEt.setText(vm.birth.value)
        binding.signup1EmailEt.setText(vm.email.value)
    }

    private fun nextCheck(){
         if(binding.signup1NameEt.text.isNotEmpty()
//             &&binding.signup1BirthEt.text.isNotEmpty()
             &&binding.signup1EmailEt.text.isNotEmpty()
             &&vm.verifySuccess.value){
             nextbutton=true
             binding.signup1NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
             binding.signup1NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
         }else{
             nextbutton=false
             binding.signup1NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
             binding.signup1NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
         }
    }

    fun Context.hideKeybord(editText: EditText){
        val imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken,0)
    }
}