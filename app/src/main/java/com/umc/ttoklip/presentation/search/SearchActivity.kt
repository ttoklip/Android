package com.umc.ttoklip.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivitySearchBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.home.HomeViewModel
import com.umc.ttoklip.presentation.home.HomeViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val viewModel: SearchViewModel by viewModels<SearchViewModelImpl>()

    override fun initView() {
    }

    override fun initObserver() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    companion object{
        const val SEARCH_ACTIVITY = "search"
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }
}