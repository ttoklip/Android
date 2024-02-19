package com.umc.ttoklip.presentation.signup.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup3Binding
import com.umc.ttoklip.databinding.ItemTermBinding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Signup3Fragment : BaseFragment<FragmentSignup3Binding>(R.layout.fragment_signup3) {

    private val viewModel: TermViewModel by viewModels()
    private lateinit var termRVAdapter:TermRVAdapter

    override fun initObserver() {
        val activity = activity as SignupActivity
        activity?.setProg(1)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    delay(300)
                    viewModel.termDatas.collect {
                        termRVAdapter = TermRVAdapter(viewModel.termDatas.value)
                        binding.signup3TermsRV.adapter = termRVAdapter
                        binding.signup3TermsRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        termRVAdapter.setMyItemClickListener(object : TermRVAdapter.MyItemClickListener {

                            override fun onItemClick(termId: Int) {
                                //fragment 이동
                                activity.updateButtonForTerm()
                                val bundle = Bundle()
                                bundle.putString("title", viewModel.termDatas.value[termId].title)
                                bundle.putString("content", viewModel.termDatas.value[termId].content)
                                findNavController().navigate(R.id.action_signup3_fragment_to_signupTerm_fragment, bundle)
                            }
                            override fun onCheckTermOn(termId: Int) {
                                viewModel.termDatas.value[termId].check = true
                                nextcheck() }
                            override fun onCheckTermOff(termId: Int) {
                                viewModel.termDatas.value[termId].check = false
                                nextcheck() }
                        })
                        viewModel.nextok.collect{
                            if(it){
                                binding.signup3NextBtn.isClickable = true
                                binding.signup3NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
                                binding.signup3NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
                            }else{
                                binding.signup3NextBtn.isClickable = false
                                binding.signup3NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                                binding.signup3NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initView() {
        viewModel.getTerm()
        binding.signup3AgreeAllIv.setOnClickListener {
            if(viewModel.allCheck.value){
                binding.signup3AgreeAllIv.setImageResource(R.drawable.oval_stroke_1)
                viewModel.allcheck(false)
                viewModel.setTermsCheck(false)
                nextcheck()
                termRVAdapter.notifyDataSetChanged()
            }else{
                binding.signup3AgreeAllIv.setImageResource(R.drawable.oval_double)
                viewModel.allcheck(true)
                viewModel.setTermsCheck(true)
                nextcheck()
                termRVAdapter.notifyDataSetChanged()
            }

        }
        binding.signup3NextBtn.setOnClickListener {
            if(viewModel.nextok.value){
                findNavController().navigate(R.id.action_signup3_fragment_to_signup4_fragment)
            }
        }
    }

    private fun nextcheck() {
        for (term in viewModel.termDatas.value) {
            if (term.check==false) {
                viewModel.nextcheck(false)
                return
            }
        }
        viewModel.nextcheck(true)
    }
}