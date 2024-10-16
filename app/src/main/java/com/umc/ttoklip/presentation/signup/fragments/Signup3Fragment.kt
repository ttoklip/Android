package com.umc.ttoklip.presentation.signup.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentSignup3Binding
import com.umc.ttoklip.presentation.base.BaseFragment
import com.umc.ttoklip.presentation.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Signup3Fragment : BaseFragment<FragmentSignup3Binding>(R.layout.fragment_signup3) {

    private lateinit var viewModel: TermViewModel
    private lateinit var termRVAdapter: TermRVAdapter
//    private val termDatas = ArrayList<TermViewModel.Term>(ArrayList())

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    delay(300)
                    viewModel.termDatas.collect {
                        termRVAdapter.notifyDataSetChanged()
                    }
                }
                launch {
                    viewModel.nextok.collect {
                        if (it) {
                            binding.signup3NextBtn.isClickable = true
                            binding.signup3NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_yellow)
                            binding.signup3NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_700)
                        } else {
                            binding.signup3NextBtn.isClickable = false
                            binding.signup3NextBtn.setBackgroundResource(R.drawable.rectangle_corner_10_strok_1_black)
                            binding.signup3NextBtn.setTextAppearance(R.style.TextAppearance_App_16sp_500)
                        }
                    }
                }
                launch{
                    viewModel.allCheck.collect{
                        if(it){
                            binding.signup3AgreeAllIv.visibility=View.VISIBLE
                            binding.signup3DeagreeAllIv.visibility=View.INVISIBLE
                            termRVAdapter.notifyDataSetChanged()
                        }else{
                            binding.signup3AgreeAllIv.visibility=View.INVISIBLE
                            binding.signup3DeagreeAllIv.visibility=View.VISIBLE
                            termRVAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    override fun initView() {
        viewModel= ViewModelProvider(requireActivity()).get(TermViewModel::class.java)
        val activity = activity as SignupActivity
        activity?.setProg(3)

        //약관 불러오기는 signup actvitiy에서

        //약관 rv 초기화하고 넣기-업데이트는 ovserve에서
        termRVAdapter = TermRVAdapter(activity,viewModel.termDatas.value)
        binding.signup3TermsRV.adapter = termRVAdapter
        binding.signup3TermsRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        //term rv click listener
        termRVAdapter.setMyItemClickListener(object : TermRVAdapter.MyItemClickListener {
            //fragment 이동
            override fun onItemClick(termId: Int) {
                findNavController().navigate(R.id.action_signup3_fragment_to_signupTerm_fragment,
                    bundleOf("termId" to termId)
                )
                activity.updateButtonForTerm()
            }

            //off인 term을 누름
            override fun onCheckTermOn(termId: Int) {
                viewModel.setTermCheck(termId, true)
                nextcheck()
            }

            //on인 term을 누름
            override fun onCheckTermOff(termId: Int) {
                viewModel.setTermCheck(termId, false)
                nextcheck()
            }
        })

        //all check off
        binding.signup3AgreeAllIv.setOnClickListener {
            viewModel.setTermsCheck(false)
            viewModel.allcheck(false)
            termRVAdapter.notifyDataSetChanged()
            nextcheck()
        }
        //all check on
        binding.signup3DeagreeAllIv.setOnClickListener {
            viewModel.setTermsCheck(true)
            viewModel.allcheck(true)
            termRVAdapter.notifyDataSetChanged()
            nextcheck()
        }


        binding.signup3NextBtn.setOnClickListener {
            if (viewModel.nextok.value) {
                findNavController().navigate(R.id.action_signup3_fragment_to_signup4_fragment)
            }
        }
    }

    private fun nextcheck() {
        for (term in viewModel.termDatas.value) {
            if (term.check == false) {
                viewModel.allcheck(false)
                viewModel.nextcheck(false)
                return
            }
        }
        viewModel.allcheck(true)
        viewModel.nextcheck(true)
    }
}