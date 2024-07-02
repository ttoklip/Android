package com.umc.ttoklip.presentation.mypage

import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityTermsPolicesBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.mypage.adapter.MyPageTermRVAdapter
import com.umc.ttoklip.presentation.signup.fragments.TermRVAdapter
import com.umc.ttoklip.presentation.signup.fragments.TermViewModel

class TermsPolicesActivity :
    BaseActivity<ActivityTermsPolicesBinding>(R.layout.activity_terms_polices) {

    private lateinit var termRVAdapter: MyPageTermRVAdapter
    private val termDatas= ArrayList<TermViewModel.Term>(ArrayList())

    override fun initView() {
        binding.termsPolicesBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val termName=getAllString(R.array.term_name)
        val termContent=getAllString(R.array.term_content)
        for(i in 0 until termName.size){
            termDatas.add(TermViewModel.Term(i,termName[i],termContent[i],false))
        }

        termRVAdapter = MyPageTermRVAdapter(this,termDatas)
        binding.termsPolicesRv.adapter = termRVAdapter
        binding.termsPolicesRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun initObserver() = Unit

    private fun getAllString(resId: Int): Array<String> {
        return resources.getStringArray(resId)
    }
}