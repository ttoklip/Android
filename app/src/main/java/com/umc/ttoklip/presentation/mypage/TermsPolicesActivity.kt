package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTermsPolicesBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.adapter.MyPageTermRVAdapter
import com.umc.ttoklip.presentation.signup.fragments.TermRVAdapter
import com.umc.ttoklip.presentation.signup.fragments.TermViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TermsPolicesActivity :
    BaseActivity<ActivityTermsPolicesBinding>(R.layout.activity_terms_polices) {

    private lateinit var termRVAdapter: MyPageTermRVAdapter
    private lateinit var viewModel: TermViewModel
//    private val termDatas= ArrayList<TermViewModel.Term>(ArrayList())

    override fun initView() {
        viewModel= ViewModelProvider(this).get(TermViewModel::class.java)
        viewModel.getTerm()

        //하드코딩
        //val termName=getAllString(R.array.term_name)
        //val termContent=getAllString(R.array.term_content)
        //for(i in 0 until termName.size){
        //  termDatas.add(TermViewModel.Term(i,termName[i],termContent[i],false))
        //}

        binding.termsPolicesBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        termRVAdapter = MyPageTermRVAdapter(this,viewModel.termDatas.value)
        binding.termsPolicesRv.adapter = termRVAdapter
        binding.termsPolicesRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //term rv click listener
        termRVAdapter.setMyItemClickListener(object : MyPageTermRVAdapter.MyItemClickListener {
            //이동
            override fun onItemClick(termId: Int) {
                val intent=Intent(this@TermsPolicesActivity,TermsPolicesDetailActivity::class.java)
                intent.putExtra("title",viewModel.termDatas.value[termId].title)
                intent.putExtra("content",viewModel.termDatas.value[termId].content)
                startActivity(intent)
            }
        })
    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    delay(300)
                    viewModel.termDatas.collect {
                        termRVAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun getAllString(resId: Int): Array<String> {
        return resources.getStringArray(resId)
    }
}