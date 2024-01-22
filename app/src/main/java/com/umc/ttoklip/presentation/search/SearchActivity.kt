package com.umc.ttoklip.presentation.search

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySearchBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()

    override fun initView() {
        binding.vm = viewModel
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    override fun initObserver() {
    }

    companion object{
        const val SEARCH_ACTIVITY = "search"
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}