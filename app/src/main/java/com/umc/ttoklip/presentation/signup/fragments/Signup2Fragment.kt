package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup1Binding
import com.umc.ttoklip.databinding.FragmentSignup2Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import java.util.regex.Pattern

class Signup2Fragment: BaseFragment<FragmentSignup2Binding>(R.layout.fragment_signup2) {

    var idok:Boolean=false
    var pwok:Boolean=false

    val num="([0-9].*)"
    val symbol="([-_.;!?@#,$%^&*].*)"
    val eng="([a-z].*)"
    val pattern_num= Pattern.compile(num)
    val pattern_symbol= Pattern.compile(symbol)
    val pattern_eng= Pattern.compile(eng)

    override fun initObserver() {
    }

    override fun initView() {
        val activity=activity as SignupActivity
        activity?.setProg(2)
        binding.signup2IdEt.addTextChangedListener {
            if(it.toString().isNotEmpty()){
                binding.signup2IdcheckButton.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
                binding.signup2IdcheckButton.setTextAppearance(R.style.TextAppearance_App_14sp_700)
            }else{
                binding.signup2IdcheckButton.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                binding.signup2IdcheckButton.setTextAppearance(R.style.TextAppearance_App_14sp_500)
            }
        }
        binding.signup2IdcheckButton.setOnClickListener {
            if(binding.signup2IdEt.text.toString().equals("aa")){
                //아이디 중복 확인 체크 로직
                idok=true
                binding.signup2IdnotokTv.visibility=View.GONE
                binding.signup2IdokTv.visibility= View.VISIBLE
            }else{
                idok=false
                binding.signup2IdnotokTv.visibility=View.VISIBLE
                binding.signup2IdokTv.visibility= View.GONE
            }
            nextOk()
        }
        binding.signup2PwEt.addTextChangedListener {
            val matcher_num=pattern_num.matcher(it.toString())
            val matcher_symbol=pattern_symbol.matcher(it.toString())
            val matcher_eng=pattern_eng.matcher(it.toString())
            if(matcher_eng.find()){
                binding.signup2PwengIv.setImageResource(R.drawable.item_pwcheck_on)
                binding.signup2PwengTv.setTextColor(ContextCompat.getColor(activity,R.color.black))
            }else{
                binding.signup2PwengIv.setImageResource(R.drawable.item_pwcheck_off)
                binding.signup2PwengTv.setTextColor(ContextCompat.getColor(activity,R.color.gray60))
            }
            if(matcher_symbol.find()){
                binding.signup2PwspeIv.setImageResource(R.drawable.item_pwcheck_on)
                binding.signup2PwspeTv.setTextColor(ContextCompat.getColor(activity,R.color.black))
            }else{
                binding.signup2PwspeIv.setImageResource(R.drawable.item_pwcheck_off)
                binding.signup2PwspeTv.setTextColor(ContextCompat.getColor(activity,R.color.gray60))
            }
            if(matcher_num.find()){
                binding.signup2PwnumIv.setImageResource(R.drawable.item_pwcheck_on)
                binding.signup2PwnumTv.setTextColor(ContextCompat.getColor(activity,R.color.black))
            }else{
                binding.signup2PwnumIv.setImageResource(R.drawable.item_pwcheck_off)
                binding.signup2PwnumTv.setTextColor(ContextCompat.getColor(activity,R.color.gray60))
            }
        }
        binding.signup2PwcheckEt.addTextChangedListener {
            if(it.toString().equals(binding.signup2PwEt.text.toString())){
                pwok=true
                binding.signupPwokTv.visibility=View.VISIBLE
                binding.signupPwnotokTv.visibility=View.GONE
            }else{
                pwok=false
                binding.signupPwokTv.visibility=View.GONE
                binding.signupPwnotokTv.visibility=View.VISIBLE
            }
            nextOk()
        }
        binding.signup2NextBtn.setOnClickListener {
            if(idok&&pwok){
                val bundle=Bundle()
                findNavController().navigate(R.id.action_signup2_fragment_to_signup3_fragment)
            }
        }
    }

    private fun nextOk(){
        if(idok&&pwok){
            binding.signup2NextBtn.isClickable=true
            binding.signup2NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
            binding.signup2NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
        }else{
            binding.signup2NextBtn.isClickable=false
            binding.signup2NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
            binding.signup2NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
        }
    }
}