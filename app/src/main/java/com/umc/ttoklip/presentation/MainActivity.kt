package com.umc.ttoklip.presentation

import android.util.Log
import androidx.activity.viewModels
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.ActivityMainBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel>()
    override fun initView() {
        viewModel.test()
    }

    override fun initObserver() {
        with(viewModel) {
            searchResult.observe(this@MainActivity) {
                Log.d("test result", it.toString())
            }
            testError.observe(this@MainActivity) {
                Log.d("test error", it)
            }
        }
    }

}